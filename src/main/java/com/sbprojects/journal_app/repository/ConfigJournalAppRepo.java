package com.sbprojects.journal_app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbprojects.journal_app.entity.ConfigJournalAppEntity;

public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId>{
    
}
