package com.sbprojects.journal_app.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    public EmailService myEmailService;

    @Disabled
    @Test
    public void test() {
        myEmailService.sendEmail("sb015002@gmail.com", 
        "testing java mail sender", 
        "hi there!!, aap kaise ho");
    }

}
