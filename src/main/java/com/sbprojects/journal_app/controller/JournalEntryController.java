package com.sbprojects.journal_app.controller;

import java.util.Collections;
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

import com.sbprojects.journal_app.dto.JournalEntryDTO;
import com.sbprojects.journal_app.entity.JournalEntry;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.JournalEntryService;
import com.sbprojects.journal_app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping("/journal")
@Tag(name = "Journal Api's", description = "Manage journal entries (Create, Read, Update, Delete).")
public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "get a journal entry", description = "Fetches all the entries of a user stored in db.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entries Fetched successfully"),
    })
    public ResponseEntity<?> getJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getUserEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Journal Entry", description = "Saves a new entry for the currently authenticated user. The date is automatically set to the current time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entry created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. empty title)"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    public ResponseEntity<JournalEntry> createEntry(@RequestBody @Valid JournalEntryDTO entryDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            JournalEntry journalEntry = new JournalEntry();
            journalEntry.setTitle(entryDTO.getTitle());
            journalEntry.setContent(entryDTO.getContent());
            journalEntry.setSentiment(entryDTO.getSentiment());

            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating journal entry", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    @Operation(summary = "Get a journal entry by its id", description = "Fetches a particular entry of a user stored in db based on its object id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getUserEntries().stream().filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            Optional<JournalEntry> myJournalEntry = journalEntryService.findById(objectId);
            if(myJournalEntry.isPresent()){
                return new ResponseEntity<>(myJournalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    @Operation(summary = "Delete a journal entry by its id", description = "Deletes a particular entry of a user from db based on its object id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entry deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deleteById(objectId, username);

        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    @Operation(summary = "Update a journal entry by its id", description = "Updates a particular entry of a user from db based on its object id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entry updated successfully"),
            @ApiResponse(responseCode = "404", description = "Entry not found")
    })
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable String id,
            @RequestBody @Valid JournalEntryDTO newEntryDTO
        ) {
            ObjectId objectId = new ObjectId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String myUserName = authentication.getName();

        User user = userService.findByUserName(myUserName);
        List<JournalEntry> collect = user.getUserEntries().stream().filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            JournalEntry oldEntry = journalEntryService.findById(objectId).orElse(null);
            if(oldEntry != null) {
                if (newEntryDTO.getTitle() != null && !newEntryDTO.getTitle().equals("")) {
                    oldEntry.setTitle(newEntryDTO.getTitle());
                }
                if (newEntryDTO.getContent() != null && !newEntryDTO.getContent().equals("")) {
                    oldEntry.setContent(newEntryDTO.getContent());
                }
                if (newEntryDTO.getSentiment() != null) {
                    oldEntry.setSentiment(newEntryDTO.getSentiment());
                }
                journalEntryService.saveEntry(oldEntry);
                log.info("Entry Updated");
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

