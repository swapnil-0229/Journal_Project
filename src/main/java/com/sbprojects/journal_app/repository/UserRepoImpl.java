package com.sbprojects.journal_app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sbprojects.journal_app.entity.User;

@Repository
public class UserRepoImpl {

    @Autowired
    public MongoTemplate mongoTemplate;
    
    public List<User> getUserForSA() {
        Query query = new Query();

        // query.addCriteria(Criteria.where("username").is("ram")); 
        
        // query.addCriteria(Criteria.where("age").gt("24"));   // where age is greater than  24
        // gt -> greater than
        // ne -> not equal
        // gte -> greater than or equal to
        // lt -> lessthan
        // lte less than or equal to

        // in -> include
        // nin -> not-include
        // query.addCriteria(Criteria.where("email").exists(true)); 
        // query.addCriteria(Criteria.where("email").ne(null).ne("")); 

        // instead of above two lines , wwe do  this by checking regular expression of email
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")); 
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true)); 
        List<User> all = mongoTemplate.find(query, User.class);
        return all;
    }
}
