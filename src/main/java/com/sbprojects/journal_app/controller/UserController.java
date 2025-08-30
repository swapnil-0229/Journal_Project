package com.sbprojects.journal_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public void createUser(@RequestBody User myUser) {
        myUserService.saveNewUser(myUser);
    }

    @PutMapping("/{oldUser}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable String oldUser) {
        User userInDb = myUserService.findByUserName(oldUser);
        if(userInDb != null) {
            userInDb.setUsername(newUser.getUsername());
            userInDb.setPassword(newUser.getPassword());
            myUserService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
