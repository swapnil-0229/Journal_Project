package com.sbprojects.journal_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.UserDetailServiceImpl;
import com.sbprojects.journal_app.service.UserService;
import com.sbprojects.journal_app.utilis.JwtUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService myUserService;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired 
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() { return "OK"; }
    

    @PostMapping("/signup")
    public void signup(@RequestBody User myUser) {
        myUserService.saveUser(myUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User myUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(myUser.getUsername(), myUser.getPassword()));

            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(myUser.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("excception while creating authentication token", e);
            return new ResponseEntity<>("incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        
    }
}
