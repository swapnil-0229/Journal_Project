package com.sbprojects.journal_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.sbprojects.journal_app.model.SentimentData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SentimentConsumerService {
    
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData){
        try {
            log.info("Consumed sentiment update for user: {}", sentimentData.getEmail());
            sendEmail(sentimentData);
        } catch (Exception e) {
            log.error("Failed to send email to user: {}", sentimentData.getEmail(), e);
        }
    }

    private void sendEmail(SentimentData sentimentData){
        emailService.sendEmail(sentimentData.getEmail(), "sentiment for previous week: ", sentimentData.getSentiment());
    }
}
