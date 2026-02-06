package com.sbprojects.journal_app.service;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserRepository;
import com.sbprojects.journal_app.utilis.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class GoogleAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String processGoogleLogin(String authorizationCode) {
        try {
            // 1. Exchange Auth Code for ID Token
            String idToken = getGoogleIdToken(authorizationCode);
            
            // 2. Get User Info from ID Token
            String userEmail = getGoogleUserEmail(idToken);

            // 3. Check/Create User in DB
            User user = handleUserRegistration(userEmail);

            // 4. Generate JWT
            return jwtUtil.generateToken(user.getUsername());

        } catch (Exception e) {
            log.error("Error processing Google login", e);
            throw new RuntimeException("Google login failed");
        }
    }

    private String getGoogleIdToken(String code) {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", "https://developers.google.com/oauthplayground");
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenEndpoint, request, Map.class);
        return (String) Objects.requireNonNull(response.getBody()).get("id_token");
    }

    private String getGoogleUserEmail(String idToken) {
        String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
        ResponseEntity<Map> response = restTemplate.getForEntity(userInfoUrl, Map.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {
            return (String) Objects.requireNonNull(response.getBody()).get("email");
        }
        throw new RuntimeException("Failed to fetch user email from Google");
    }

    private User handleUserRegistration(String email) {
        User user = userRepository.findByUsername(email); 
        if (user == null) {
            log.info("New Google user detected: {}", email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(email);
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            newUser.setRoles(Arrays.asList("USER"));
            newUser.setSentimentAnalysis(true);
            return userRepository.save(newUser);
        }
        return user;
    }
}