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
import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserRepository;
import com.sbprojects.journal_app.service.UserService;
import com.sbprojects.journal_app.service.WeatherService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User Api's", description = "read, update and delete user")
public class UserController {

    @Autowired
    private UserService myUserService;

    @Autowired 
    private UserRepository myUserRepo;

    @Autowired
    private WeatherService weatherService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String oldUser = authentication.getName();
        User userInDb = myUserService.findByUserName(oldUser);

        userInDb.setUsername(newUser.getUsername());
        userInDb.setPassword(newUser.getPassword()); 
        myUserService.saveUser(userInDb);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        myUserRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
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
