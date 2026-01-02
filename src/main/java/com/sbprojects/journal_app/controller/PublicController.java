package com.sbprojects.journal_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.UserDetailServiceImpl;
import com.sbprojects.journal_app.service.UserService;
import com.sbprojects.journal_app.utilis.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public Api's")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired 
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() { return "OK"; }
    

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during user signup: ", e);
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            log.warn("Login failed for user: {}", user.getUsername());
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            log.error("Unexpected error during login", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}