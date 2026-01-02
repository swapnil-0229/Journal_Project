package com.sbprojects.journal_app.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.cache.AppCache;
import com.sbprojects.journal_app.dto.UserDTO;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Api's", description = "Administrative operations for managing users and application settings.")
public class AdminController {

    @Autowired
    public UserService userService;

    @Autowired
    public AppCache appCache;

    @GetMapping("/all-users")
    @Operation(summary = "Get all users", description = "Fetches a list of all users registered in the application. This endpoint is restricted to Admins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Access Denied")
    })
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-admin")
    @Operation(summary = "Create a new Admin", description = "Creates a new user with ADMIN role privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> createAdmin(@RequestBody @Valid UserDTO userDTO){
        try {
            User newAdmin = User.builder()
            .username(userDTO.getUsername())
            .password(userDTO.getPassword())
            .email(userDTO.getEmail())
            .sentimentAnalysis(userDTO.isSentimentAnalysis())
            .build();
            userService.saveAdmin(newAdmin);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/clear-app-cache")
    @Operation(summary = "Clear Application Cache", description = "Reloads the application cache (e.g., App Config properties) without restarting the server.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cache cleared successfully")
    })
    public void clearAppCache() {
        appCache.init();
    }
}
