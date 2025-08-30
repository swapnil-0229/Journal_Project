// package com.sbprojects.journal_app.controller;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import com.sbprojects.journal_app.entity.journalEntry;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @RestController
// @RequestMapping("/journal")
// public class journalEntryController {

//     private Map<Long, journalEntry> journalEntries = new HashMap<>();

//     @GetMapping
//     public List<journalEntry> getAll(){
//         return new ArrayList<>(journalEntries.values());
//     }

//     @PostMapping
//     public boolean createEntry(@RequestBody journalEntry myEntry) {
//         journalEntries.put(myEntry.getId(), myEntry);
//         return true;
//     }

//     @GetMapping("id/{myId}")
//     public journalEntry getJournalEntryById(@PathVariable long myId) {
//         return journalEntries.get(myId);
//     }

//     @DeleteMapping("id/{myId}")
//     public boolean deleteJournalEntryById(@PathVariable long myId) {
//         journalEntries.remove(myId);
//         return true;
//     }

//     @PutMapping("id/{myId}")
//     public boolean updateJournalEntryById(@PathVariable long myId, @RequestBody journalEntry myEntry) {
//         journalEntries.put(myId, myEntry);
//         return true;
//     }


// }


// we have not added request status, so we move to  v2 of controller