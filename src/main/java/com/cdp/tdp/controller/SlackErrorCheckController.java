package com.cdp.tdp.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class SlackErrorCheckController {
    @GetMapping( "/alarm")
    public ResponseEntity<?> doAlarmCheck(){
        int i = 1/0;
        return ResponseEntity.ok("Alarm Check Ok");
    }
}
