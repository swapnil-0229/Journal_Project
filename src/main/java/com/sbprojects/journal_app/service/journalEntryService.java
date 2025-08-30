package com.sbprojects.journal_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sbprojects.journal_app.entity.journalEntry;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.journalEntryRepo;

@Component
public class journalEntryService {
    
    @Autowired
    private journalEntryRepo myEntryRepo;

    @Autowired
    private userService myUserService;

    @Transactional
    public void saveEntry(journalEntry myEntry, String username) {
        try {
            User myUser = myUserService.findByUserName(username);
            myEntry.setDate(LocalDateTime.now());
            journalEntry saved = myEntryRepo.save(myEntry);
            myUser.getUserEntries().add(saved);
            myUserService.saveEntry(myUser);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Something has gone wrong while saving the entry.", e);
        }
    }

    public void saveEntry(journalEntry myEntry) {
        myEntryRepo.save(myEntry);
    }

    public List<journalEntry> getAll() {
        return myEntryRepo.findAll();
    }

    public Optional<journalEntry> findById(ObjectId id){ 
        return myEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id, String username) {
        User myUser = myUserService.findByUserName(username);
        myUser.getUserEntries().removeIf(x -> x.getId().equals(id));
        myUserService.saveEntry(myUser);
        myEntryRepo.deleteById(id);
    }
 }
