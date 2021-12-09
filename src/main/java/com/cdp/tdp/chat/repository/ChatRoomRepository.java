package com.cdp.tdp.chat.repository;


import com.cdp.tdp.chat.domain.ChatRoom;
import com.cdp.tdp.chat.dto.ChatRoomDTO;
import com.cdp.tdp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Stream;

@Repository

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    Optional<User> findByUsername(String username);
//    Optional<User> findByKakaoId(Long kakaoId);

}

//public class ChatRoomRepository {
//
//    private Map<String, ChatRoomDTO> chatRoomDTOMap;
//
//    @PostConstruct
//    private void init(){
//        chatRoomDTOMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoom> findAllRooms(){
//        //채팅방 생성 순서 최근 순으로 반환
//        List<ChatRoom> result = new ArrayList<>(chatRoomDTOMap.values());
//        Collections.reverse(result);
//
//        return result;
//    }
//
//    public ChatRoomDTO findRoomById(String id){
//        return chatRoomDTOMap.get(id);
//    }
//
//    public ChatRoomDTO createChatRoomDTO(String name){
//        ChatRoomDTO room = ChatRoomDTO.create(name);
//        chatRoomDTOMap.put(room.getRoomId(), room);
//
//        return room;
//    }
//  }