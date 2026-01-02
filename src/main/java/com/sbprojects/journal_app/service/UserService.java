package com.sbprojects.journal_app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserRepository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j  
public class UserService {

    @Autowired 
    private UserRepository userEntryRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(@NonNull User myUser) {
        if (!myUser.getPassword().startsWith("$2a$")) {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        }

        myUser.setRoles(Arrays.asList("USER"));
        userEntryRepo.save(myUser);
    }

    public void saveAdmin(@NonNull User myUser) {
        if (!myUser.getPassword().startsWith("$2a$")) {
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        }

        myUser.setRoles(Arrays.asList("USER", "ADMIN"));
        userEntryRepo.save(myUser);
    }

    public List<User> getAll() {
        return userEntryRepo.findAll();
    }

    public Optional<User> findById(@NonNull ObjectId id){ 
        return userEntryRepo.findById(id);
    }

    public void deleteById(@NonNull ObjectId myId) {
        userEntryRepo.deleteById(myId);
    }

    public User findByUserName(@NonNull String username) {
        return userEntryRepo.findByUsername(username);
    }
}
