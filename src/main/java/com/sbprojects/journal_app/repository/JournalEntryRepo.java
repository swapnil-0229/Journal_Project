package com.sbprojects.journal_app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbprojects.journal_app.entity.JournalEntry;

public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId>{
}


// controller calls ->>> service calls ->>> repository