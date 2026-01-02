package com.sbprojects.journal_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbprojects.journal_app.api.response.WeatherResponse;
import com.sbprojects.journal_app.dto.UserDTO;
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserRepository;
import com.sbprojects.journal_app.service.UserService;
import com.sbprojects.journal_app.service.WeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "User Api's", description = "Operations for managing the user's own profile (Update, Delete, Greetings).")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired 
    private UserRepository userRepo;

    @Autowired
    private WeatherService weatherService;

    @PutMapping("/update")
    @Operation(summary = "Update User Profile", description = "Updates username and password for the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user not found")
    })
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oldUser = authentication.getName();
        User userInDb = userService.findByUserName(oldUser);

        if (userInDb != null) {
            userInDb.setUsername(userDTO.getUsername());
            userInDb.setPassword(userDTO.getPassword());
            userService.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete User Account", description = "Permanently deletes the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully")
    })
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get Weather Greeting", description = "Returns a personalized greeting with current weather info.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Greeting fetched successfully")
    })
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Manali");
        String greetings = "";
        if(weatherResponse != null) {
            greetings += ", Weather feels like " + weatherResponse.getCurrent().getFeelsLike() +  " , and can be described by " + 
            weatherResponse.getCurrent().getCondition().getDescription() ;
        }
        return new ResponseEntity<>("Hi "+ authentication.getName()+ greetings, HttpStatus.OK);
    }
}
