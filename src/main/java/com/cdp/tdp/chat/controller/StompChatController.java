package com.cdp.tdp.chat.controller;

import com.cdp.tdp.chat.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    public List<String> count = new ArrayList<>();

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){
        log.info(message.getMessage());
        log.info("채팅방 들어옴");
        count.add(message.getWriter());

//        if (message.getMessage()=="enter") {
//            count.add(message.getWriter());
//            log.info("enter :" + message.getWriter());
//        }
//        if (message.getMessage()=="exit"){
//            count.remove(message.getWriter());
//            log.info("exit :" + message.getWriter());
//        }


        message.setUserCount((long) count.size());
        log.info(String.valueOf(count.size()));

        String json = new Gson().toJson(count);
        message.setMessage(json);
        template.convertAndSend("/sub/chat/home/", message);

        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

    }

    @MessageMapping(value = "/chat/exit")
    public void exit(ChatMessageDTO message) {
        log.info(message.getMessage());
        log.info("채팅방 나감");
        count.remove(message.getWriter());
        message.setUserCount((long) count.size());

        String json = new Gson().toJson(count);
        message.setMessage(json);
        template.convertAndSend("/sub/chat/home/", message);

        message.setMessage(message.getWriter() + "님이 채팅방을 나갔습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

    }


    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){

        log.info("send message");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

    }


}