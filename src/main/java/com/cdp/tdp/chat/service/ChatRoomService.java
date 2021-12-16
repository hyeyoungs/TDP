package com.cdp.tdp.chat.service;

import com.cdp.tdp.chat.domain.ChatRoom;
import com.cdp.tdp.chat.dto.ChatRoomDTO;
import com.cdp.tdp.chat.repository.ChatRoomRepository;
import com.cdp.tdp.domain.Tag;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.repository.TagRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public List<ChatRoom> getAllRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom createRoom(ChatRoomDTO chatRoomDTO,Long id)  {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such user"));
        ChatRoom room=new ChatRoom(chatRoomDTO,user);
        chatRoomRepository.save(room);
        return room;
    }

    public ChatRoom getRoom(Long id) {
        return chatRoomRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 채팅방이 존재하지 않습니다")
        );
    }

    public boolean checkmyroom(Long room_id,Long user_id){
       ChatRoom chatRoom=chatRoomRepository.findById(room_id).orElseThrow(
               ()-> new NullPointerException("해당 채팅방이 존재하지 않습니다")
       );
       if(chatRoom.getUser().getId().equals(user_id)){
            return true;
       }
       return false;
    }

    public void deleteRoom(Long id) {
        chatRoomRepository.deleteByRoomId(id);
    }
}
