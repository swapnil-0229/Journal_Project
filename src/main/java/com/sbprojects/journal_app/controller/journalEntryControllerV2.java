// package com.sbprojects.journal_app.controller;

// import java.time.LocalDateTime;
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
// import com.sbprojects.journal_app.service.journalEntryService;

// @RestController
// @RequestMapping("/journal")
// public class journalEntryControllerV2 {
    
//     @Autowired
//     private journalEntryService myEntryService;

//     @GetMapping
//     public ResponseEntity<?> getAll(){
//         List<journalEntry> all = myEntryService.getAll();
//         if(all != null && !all.isEmpty()) return new ResponseEntity<>(all, HttpStatus.OK);
//         else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @PostMapping
//     public ResponseEntity<journalEntry> createEntry(@RequestBody journalEntry myEntry) {
//         try {
//             myEntry.setDate(LocalDateTime.now());
//             myEntryService.saveEntry(myEntry);
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
//         }

//     @DeleteMapping("id/{myId}")
//     public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
//         if(myEntryService.findById(myId).isPresent()) {
//             myEntryService.deleteById(myId);
//             return new ResponseEntity<>(HttpStatus.ACCEPTED);
//         }
//         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }

//     @PutMapping("id/{myId}")
//     public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody journalEntry newEntry) {
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


// this controller is not connected to particular user so we move to  version 3 of this controller