package com.sbprojects.journal_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class healthCheck {

    @GetMapping("/health-check")
    public String health() {
        return "OK";
    }
}
