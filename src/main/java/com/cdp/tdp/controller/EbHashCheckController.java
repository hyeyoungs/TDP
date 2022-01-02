package com.cdp.tdp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbHashCheckController {
    @GetMapping( "/eb/check")
    public ResponseEntity<?> doHealthCheck() {
        return ResponseEntity.ok("HealthCheck OK");
    }
}
