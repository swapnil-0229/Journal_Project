package com.sbprojects.journal_app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbprojects.journal_app.entity.journalEntry;

public interface journalEntryRepo extends MongoRepository<journalEntry, ObjectId>{
}


// controller calls ->>> service calls ->>> repository