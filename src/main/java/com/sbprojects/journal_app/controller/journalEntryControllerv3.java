// package com.sbprojects.journal_app.controller;

// import java.util.List;
// import java.util.Optional;

// import org.bson.types.ObjectId;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.sbprojects.journal_app.entity.journalEntry;
// import com.sbprojects.journal_app.entity.User;
// import com.sbprojects.journal_app.service.journalEntryService;
// import com.sbprojects.journal_app.service.userService;

// @RestController
// @RequestMapping("/journal")
// public class journalEntryControllerv3 {
//     @Autowired
//     private journalEntryService myEntryService;

//     @Autowired
//     private userService myUserService;

//     @GetMapping("/{username}")
//     public ResponseEntity<?> getJournalEntriesOfUser(@PathVariable String username){
//         User myUser = myUserService.findByUserName(username);
//         List<journalEntry> all = myUser.getUserEntries();

//         if(all != null && !all.isEmpty()) return new ResponseEntity<>(all, HttpStatus.OK);
//         else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @PostMapping("/{username}")
//     public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry myEntry, @PathVariable String username) {
//         try {
//             myEntryService.saveEntry(myEntry, username);
//             return new ResponseEntity<>(myEntry, HttpStatus.OK);
//         } catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }
//     }

//     @GetMapping("id/{myId}")
//     public ResponseEntity<journalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
//         Optional<journalEntry> someEntry =  myEntryService.findById(myId);

//         if(someEntry.isPresent()) {
//             return new ResponseEntity<>(someEntry.get(), HttpStatus.OK);
//         }
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @DeleteMapping("id/{username}/{myId}")
//     public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String username) {
//         if(myEntryService.findById(myId).isPresent()) {
//             myEntryService.deleteById(myId, username);
//             return new ResponseEntity<>(HttpStatus.ACCEPTED);
//         }
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @PutMapping("id/{username}/{myId}")
//     public ResponseEntity<?> updateJournalEntryById(
//             @PathVariable ObjectId myId, 
//             @RequestBody journalEntry newEntry,
//             @PathVariable String username
//         ) {
//         journalEntry oldEntry = myEntryService.findById(myId).orElse(null);
//         if(oldEntry != null) {
//             if(newEntry.getTitle() != null) {
//                 oldEntry.setTitle(newEntry.getTitle());
//             }
//             if(newEntry.getContent() != null) {
//                 oldEntry.setContent(newEntry.getContent());
//             }
//             myEntryService.saveEntry(oldEntry);
//             return new ResponseEntity<>(oldEntry, HttpStatus.OK);
//         }
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }
// }
