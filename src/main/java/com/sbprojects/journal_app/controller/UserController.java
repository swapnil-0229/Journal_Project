package com.sbprojects.journal_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.userService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userService myUserService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oldUser = authentication.getName();
        User userInDb = myUserService.findByUserName(oldUser);
        userInDb.setUsername(newUser.getUsername());
        userInDb.setPassword(newUser.getPassword());
        myUserService.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
