package com.sbprojects.journal_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.dto.UserDTO;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.UserDetailServiceImpl;
import com.sbprojects.journal_app.service.UserService;
import com.sbprojects.journal_app.utilis.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import jakarta.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public Api's", description = "Endpoints accessible to everyone (Login, Signup, Health Check).")
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
    @Operation(summary = "Health Check", description = "Checks if the application is running correctly.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Server is up and running")
    })
    public String healthCheck() { return "OK"; }
    

    @PostMapping("/signup")
    @Operation(summary = "Register New User", description = "Creates a new user account with the provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "User already exists or invalid input")
    })
    public ResponseEntity<?> signup(@RequestBody @Valid UserDTO userDTO) {
        try {
            User newUser = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .sentimentAnalysis(userDTO.isSentimentAnalysis())
                .build();

            userService.saveUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during user signup: ", e);
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login User", description = "Authenticates a user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "401", description = "Incorrect username or password"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<String> login(@RequestBody @Valid UserDTO userDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));

            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(userDTO.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            log.warn("Login failed for user: {}", userDTO.getUsername());
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            log.error("Unexpected error during login", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}