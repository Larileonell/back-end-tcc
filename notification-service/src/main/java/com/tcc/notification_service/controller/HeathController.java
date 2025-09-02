package com.tcc.notification_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeathController {
    @GetMapping("/healthz")
    public String health() {
        return "notification-service OK";
    }
}
