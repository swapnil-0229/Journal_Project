package com.sbprojects.journal_app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
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

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.entity.journalEntry;
import com.sbprojects.journal_app.service.journalEntryService;
import com.sbprojects.journal_app.service.userService;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV4 {

    @Autowired
    private journalEntryService myEntryService;

    @Autowired
    private userService myUserService;

    @GetMapping
    public ResponseEntity<?> getJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName(); // gets us username from current context

        User myUser = myUserService.findByUserName(myUserName);
        List<journalEntry> all = myUser.getUserEntries();

        if(all != null && !all.isEmpty()) return new ResponseEntity<>(all, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry myEntry) {
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
    public ResponseEntity<journalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName();

        User user = myUserService.findByUserName(myUserName);
        List<journalEntry> collect = user.getUserEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            Optional<journalEntry> myJournalEntry = myEntryService.findById(myId);
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
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody journalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName();

        journalEntry oldEntry = myEntryService.findById(myId).orElse(null);

        User user = myUserService.findByUserName(myUserName);
        List<journalEntry> collect = user.getUserEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            if(oldEntry != null) {
                if(newEntry.getTitle() != null) {
                    oldEntry.setTitle(newEntry.getTitle());
                }
                if(newEntry.getContent() != null) {
                    oldEntry.setContent(newEntry.getContent());
                }
                myEntryService.saveEntry(oldEntry);
                System.out.println("Entry Updated");
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
