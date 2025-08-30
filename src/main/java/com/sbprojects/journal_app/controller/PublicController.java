package com.sbprojects.journal_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private userService myUserService;

    @PostMapping("/create_user")
    public void createUser(@RequestBody User myUser) {
        myUserService.saveNewUser(myUser);
    }
}
