package com.sbprojects.journal_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbprojects.journal_app.entity.JournalEntry;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.JournalEntryRepo;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JournalEntryService {
    
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(@NonNull JournalEntry journalEntry, @NonNull String username) {
        try {
            User user = userService.findByUserName(username);

            if (user == null) {
                log.error("Cannot save entry. User not found with username: {}", username);
                throw new RuntimeException("User not found");
            }

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);

            user.getUserEntries().add(saved);
            userService.saveUser(user);
            
            log.info("New journal entry saved for user: {}", username);
        } catch (Exception e) {
            log.error("Error occurred while saving entry for user: {}", username, e);
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

    public void saveEntry(@NonNull JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(@NonNull ObjectId id){ 
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteById(@NonNull ObjectId id, @NonNull String username) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(username);
            if(user == null) {
                log.warn("Attempted to delete entry from non-existent user: {}", username);
                return false; 
            }

            removed = user.getUserEntries().removeIf(x -> x.getId().equals(id));

            if (removed) {
                userService.saveUser(user);
                journalEntryRepo.deleteById(id);
                log.info("Deleted entry ID: {} from user: {}", id, username);
            } else {
                log.warn("Entry ID: {} not found in user: {}", id, username);
            }
            
        } catch (Exception e) {
            log.error("Error occurred while deleting entry ID: {} for user: {}", id, username, e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }
}