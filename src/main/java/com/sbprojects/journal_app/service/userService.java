package com.sbprojects.journal_app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserEntryRepo;
import org.springframework.security.crypto.password.PasswordEncoder;


@Component
public class UserService {

    @Autowired 
    private UserEntryRepo myUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User myUser) {
        if (!myUser.getPassword().startsWith("$2a$")) {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        }
        myUser.setRoles(Arrays.asList("USER"));
        myUserRepo.save(myUser);
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
