package com.sbprojects.journal_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService myUserService;

    @GetMapping("/health-check")
    public String healthCheck() { return "OK"; }
    

    @PostMapping("/create-user")
    public void createUser(@RequestBody User myUser) {
        myUserService.saveUser(myUser);
    }
}
