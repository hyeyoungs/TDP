package com.cdp.tdp.chat.domain;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.Timestamped;
import com.cdp.tdp.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@Entity // DB 테이블 역할을 합니다
public class ChatUser extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "ROOM_ID", nullable = false )
    private ChatRoom chatRoom;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    public ChatUser(User user,ChatRoom chatRoom) {
        this.chatRoom=chatRoom;
        this.user = user;

    }

}