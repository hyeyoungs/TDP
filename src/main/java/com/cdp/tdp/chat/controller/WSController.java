//package com.cdp.tdp.chat.controller;
//
//import com.cdp.tdp.chat.WSService;
//import com.cdp.tdp.chat.dto.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@Slf4j
//@RestController
//public class WSController {
//
//    @Autowired
//    private WSService service;
//
//    @PostMapping("/send-message")
//    public void sendMessage(@RequestBody final Message message) {
//        service.notifyFrontend(message.getMessageContent());
//    }
//
//    @PostMapping("/send-private-message")
//    public void sendPrivateMessage(@RequestBody final String id,
//                                   @RequestBody final Message message) {
//        log.info(id);
//        service.notifyUser(id, message.getMessageContent());
//    }
//}
