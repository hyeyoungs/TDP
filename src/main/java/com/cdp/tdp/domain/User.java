package com.cdp.tdp.domain;

import com.cdp.tdp.chat.domain.ChatRoom;
import com.cdp.tdp.chat.domain.ChatUser;
import com.cdp.tdp.dto.UserUpdateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends Timestamped {

    public User(String signId, String signPassword, String nickname, String githubId, String introduce,String picture,String picture_real) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = githubId;
        this.introduce = introduce;
        this.picture=picture;
        this.picture_real=picture_real; // s3이미지 주소
        this.kakaoId=null;

    }

    public User(String signId, String signPassword, String nickname, String githubId, String introduce,String picture,String picture_real,Long kakaoId) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = githubId;
        this.introduce = introduce;
        this.picture=picture;
        this.picture_real=picture_real; // s3이미지 주소
        this.kakaoId=kakaoId;

    }

    public User(String signId, String signPassword, String nickname, Long kakaoId) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = null;
        this.introduce = null;
        this.picture= null;
        this.picture_real= null; // s3이미지 주소
        this.kakaoId=kakaoId;

    }
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String github_id;

    private String introduce;

    private String picture;

    private String picture_real;

    private Long kakaoId;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Til> til_list;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Likes> likes;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ChatRoom> chatRooms;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ChatUser> chatUser;

    public void updateUser(UserUpdateDto userUpdateDto){
        this.nickname = userUpdateDto.getNickname();
        this.github_id = userUpdateDto.getGithub_id();
        this.introduce = userUpdateDto.getIntroduce();
        this.picture= userUpdateDto.getPicture();
        this.picture_real = userUpdateDto.getPicture_real();
    }


}
