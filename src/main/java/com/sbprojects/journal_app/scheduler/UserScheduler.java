package com.sbprojects.journal_app.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.sbprojects.journal_app.cache.AppCache;
import com.sbprojects.journal_app.entity.JournalEntry;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.enums.Sentiment;
import com.sbprojects.journal_app.repository.UserRepoImpl;
import com.sbprojects.journal_app.service.EmailService;

@Component
public class UserScheduler {
    
    @Autowired
    public EmailService myEmailService;

    @Autowired
    public UserRepoImpl userRepo;

    @Autowired
    public AppCache appCache;

    // @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> all = userRepo.getUserForSA();

        for(User user: all) {
            List<JournalEntry> journalEntries = user.getUserEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());

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
                myEmailService.sendEmail(user.getEmail(), "Sentiment Analysis for last 7 days", mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void clearAppCache(){ 
        appCache.init();
    }
}
