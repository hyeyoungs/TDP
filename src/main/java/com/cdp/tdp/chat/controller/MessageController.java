//package com.cdp.tdp.chat.controller;
//
//import com.cdp.tdp.chat.NotificationService;
//import com.cdp.tdp.chat.dto.Message;
//import com.cdp.tdp.chat.dto.ResponseMessage;
//import com.cdp.tdp.domain.User;
//import com.cdp.tdp.security.UserDetailsImpl;
//import com.cdp.tdp.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.annotation.SendToUser;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.util.HtmlUtils;
//@RequiredArgsConstructor
//@Controller
//public class MessageController {
//
//
//    private final NotificationService notificationService;
//    private final UserRepository userRepository;
//
//
//
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public ResponseMessage getMessage(final Message message) throws InterruptedException {
//        Thread.sleep(1000);
//        notificationService.sendGlobalNotification();
//        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()));
//    }
//
//    @MessageMapping("/private-message")
//    @SendToUser("/topic/private-messages")
//    public ResponseMessage getPrivateMessage(final Message message, @AuthenticationPrincipal UserDetailsImpl userDetails) throws InterruptedException {
//        Thread.sleep(1000);
//        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("no such user"));
//
//        notificationService.sendPrivateNotification(user.getId().toString());
//        return new ResponseMessage(HtmlUtils.htmlEscape(
//                "Sending private message to user " + userDetails.getUsername() + ": "
//                        + message.getMessageContent())
//        );
//    }
//}
