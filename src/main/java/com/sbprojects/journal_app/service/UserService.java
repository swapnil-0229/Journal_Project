package com.sbprojects.journal_app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserEntryRepo;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j  
public class UserService {

    @Autowired 
    private UserEntryRepo myUserRepo;

    // we are using Slf4j notation so we don't need this defination of logger
    // Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveUser(User myUser) {
        try {
            if (!myUser.getPassword().startsWith("$2a$")) {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            }
            myUser.setRoles(Arrays.asList("USER"));
            myUserRepo.save(myUser);
            return true;
        } catch (Exception e) {
            log.info("hahahahhaha  {}", myUser.getUsername(),  e);
            // logger.warn("hahahahhaha");   // we use log with slf4j notation
            // logger.error("hahahahhaha");
            // logger.debug("hahahahhaha");
            // logger.trace("hahahahhaha");
            System.out.println("Some error occured while saving user" + e);
            return false;
        }
    }

    public void saveAdmin(User myUser) {
        if (!myUser.getPassword().startsWith("$2a$")) {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        }
        myUser.setRoles(Arrays.asList("USER", "ADMIN"));
        myUserRepo.save(myUser);
    }

    public List<User> getAll() {
        return myUserRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){ 
        return myUserRepo.findById(id);
    }

    public void deleteById(ObjectId myId) {
        myUserRepo.deleteById(myId);
    }

    public User findByUserName(String username) {
        return myUserRepo.findByusername(username);
    }
}
