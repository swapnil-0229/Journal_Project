package com.sbprojects.journal_app.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTests {

    @Autowired
    public UserSARepository userRepoImpl;

    @Disabled
    @Test
    public void test() {
        userRepoImpl.getUserForSA();
    }
}
