package com.sbprojects.journal_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sbprojects.journal_app.entity.User;
import com.sbprojects.journal_app.repository.UserEntryRepo;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    public UserEntryRepo myRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = myRepo.findByusername(username);
        if(user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
        }
        throw new UsernameNotFoundException("User not found with username " + username);
    }
}
