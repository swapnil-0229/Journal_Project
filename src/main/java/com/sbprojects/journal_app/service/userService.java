package com.sbprojects.journal_app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.userEntryRepo;
import org.springframework.security.crypto.password.PasswordEncoder;


@Component
public class userService {

    @Autowired 
    private userEntryRepo myUserRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User myUser) {
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUser.setRoles(Arrays.asList("USER"));
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
