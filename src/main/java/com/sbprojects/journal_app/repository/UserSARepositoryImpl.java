package com.sbprojects.journal_app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sbprojects.journal_app.entity.User;

@Repository
public class UserSARepositoryImpl implements UserSARepository{

    @Autowired
    public MongoTemplate mongoTemplate;
    
    public List<User> getUserForSA() {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")); 
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true)); 
        List<User> all = mongoTemplate.find(query, User.class);
        return all;
    }
}
