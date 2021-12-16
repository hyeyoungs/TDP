package com.cdp.tdp.chat.controller;

import com.cdp.tdp.chat.domain.ChatRoom;
import com.cdp.tdp.chat.dto.ChatRoomDTO;
import com.cdp.tdp.chat.repository.ChatUserRepository;
import com.cdp.tdp.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class RoomController {

    private final ChatRoomService chatRoomService;
    private final ChatUserRepository chatUserRepository;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public List<ChatRoom> rooms() {
        return chatRoomService.getAllRooms();
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public ChatRoom create(@RequestBody ChatRoomDTO chatRoomDTO) {
        return chatRoomService.createRoom(chatRoomDTO);
    }

    //채팅방 조회
    @GetMapping("/room/{id}")
    public ChatRoom getRoom(@PathVariable final Long id) {

        return chatRoomService.getRoom(id);
    }

    // 채팅벙 전체 이용자 수 조회
    @GetMapping("/rooms/users")
    public int getChatUsers(@PathVariable final Long id) {
        Long users=chatUserRepository.count();
        int users_count=users.intValue();
        return users_count;
    }


}