package com.sbprojects.journal_app.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sbprojects.journal_app.cache.AppCache;
import com.sbprojects.journal_app.entity.JournalEntry;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.enums.Sentiment;
import com.sbprojects.journal_app.model.SentimentData;
import com.sbprojects.journal_app.repository.UserRepository;
import com.sbprojects.journal_app.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserScheduler {
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppCache appCache;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() { 
        log.info("Starting Weekly Sentiment Analysis Job...");
        
        List<User> all = userRepository.getUserForSA();

        for(User user: all) {
            try {
                List<JournalEntry> journalEntries = user.getUserEntries();
                
                List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

                Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

                for(Sentiment sentiment: sentiments) {
                    if(sentiment != null) {
                        sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0)+1);
                    }
                }

                Sentiment mostFrequentSentiment = null;
                int maxCount = 0;

                for(Map.Entry<Sentiment, Integer> entry: sentimentCounts.entrySet()) {
                    if(entry.getValue() > maxCount) {
                        maxCount = entry.getValue(); 
                        mostFrequentSentiment = entry.getKey();
                    }
                }

                if(mostFrequentSentiment != null) {
                    SentimentData sentimentData = SentimentData.builder()
                        .email(user.getEmail())
                        .sentiment("Sentiment Analysis for last 7 days: " + mostFrequentSentiment)
                        .build();
                    
                    try {
                        kafkaTemplate.send(
                            "weekly-sentiments",
                            Objects.requireNonNull(sentimentData.getEmail()),
                            sentimentData
                        );
                        log.info("Sent sentiment Kafka message for user: {}", user.getEmail());
                        
                    } catch (Exception e) {
                        log.error("Kafka failed for user: {}. Fallback to email.", user.getEmail());
                        emailService.sendEmail(
                            sentimentData.getEmail(), 
                            "Sentiment for previous week", 
                            sentimentData.getSentiment()
                        );
                    }
                }
            } catch (Exception e) {
                log.error("Error processing sentiment for user: {}", user.getUsername(), e);
            }
        }
        log.info("Weekly Sentiment Analysis Job Completed.");
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void clearAppCache(){   
        appCache.init();
    }
}