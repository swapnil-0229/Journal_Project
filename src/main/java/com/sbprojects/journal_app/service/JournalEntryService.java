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

@Service
public class JournalEntryService {
    
    @Autowired
    private JournalEntryRepo myEntryRepo;

    @Autowired
    private UserService myUserService;

    @Transactional
    public void saveEntry(JournalEntry myEntry, String username) {
        try {
            User myUser = myUserService.findByUserName(username);
            myEntry.setDate(LocalDateTime.now());
            JournalEntry saved = myEntryRepo.save(myEntry);
            myUser.getUserEntries().add(saved);
            myUserService.saveUser(myUser);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Something has gone wrong while saving the entry.", e);
        }
    }

    public void saveEntry(JournalEntry myEntry) {
        myEntryRepo.save(myEntry);
    }

    public List<JournalEntry> getAll() {
        return myEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){ 
        return myEntryRepo.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String username) {
        try {
            User myUser = myUserService.findByUserName(username);
            boolean removed = myUser.getUserEntries().removeIf(x -> x.getId().equals(id));

            if(removed) {
                myUserService.saveUser(myUser);
                myEntryRepo.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting journal entry", e);
        }
    }
 }
