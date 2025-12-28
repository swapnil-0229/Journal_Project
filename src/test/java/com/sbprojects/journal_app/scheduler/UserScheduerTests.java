package com.sbprojects.journal_app.scheduler;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserScheduerTests {
    
    @Autowired
    public UserScheduler myScheduler;

    @Test
    public void test(){
        myScheduler.fetchUsersAndSendSaMail();
    }
}
