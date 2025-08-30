package com.sbprojects.journal_app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.sbprojects.journal_app.entity.User;

public interface userEntryRepo extends MongoRepository<User, ObjectId>{
    User findByusername(String username);
}
