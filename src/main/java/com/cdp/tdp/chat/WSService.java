package com.cdp.tdp.chat;

import com.cdp.tdp.chat.dto.ResponseMessage;
import com.cdp.tdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService,  UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    public void notifyFrontend(final String message) {
        ResponseMessage response = new ResponseMessage(message);

        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(final String id, final String message) {
        ResponseMessage response = new ResponseMessage(message);

        notificationService.sendPrivateNotification(id);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
