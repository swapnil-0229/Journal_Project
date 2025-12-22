package com.sbprojects.journal_app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.entity.JournalEntry;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.JournalEntryService;
import com.sbprojects.journal_app.service.UserService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/journal")
public class JournalEntryController {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryController.class);

    @Autowired
    private JournalEntryService myEntryService;

    @Autowired
    private UserService myUserService;

    @GetMapping
    public ResponseEntity<?> getJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName(); // gets us username from current context

        User myUser = myUserService.findByUserName(myUserName);
        List<JournalEntry> all = myUser.getUserEntries();

        if(all != null && !all.isEmpty()) return new ResponseEntity<>(all, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String myUserName = authentication.getName();

            myEntryService.saveEntry(myEntry, myUserName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName();

        User user = myUserService.findByUserName(myUserName);
        List<JournalEntry> collect = user.getUserEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            Optional<JournalEntry> myJournalEntry = myEntryService.findById(myId);
            if(myJournalEntry.isPresent()){
                return new ResponseEntity<>(myJournalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName();

        if(myEntryService.findById(myId).isPresent()) {
            myEntryService.deleteById(myId, myUserName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName();

        JournalEntry oldEntry = myEntryService.findById(myId).orElse(null);

        User user = myUserService.findByUserName(myUserName);
        List<JournalEntry> collect = user.getUserEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            if(oldEntry != null) {
                if(newEntry.getTitle() != null) {
                    oldEntry.setTitle(newEntry.getTitle());
                }
                myEntryService.saveEntry(oldEntry);
                logger.info("Entry Updated");
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

