package com.cdp.tdp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자 만든다.
@Entity // DB 테이블 역할
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

    @Column(nullable = true)
    private Long kakaoId;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Til> til_list;


    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Comment> comments;

    public void updateUser(String nickname, String githubId, String fileName, String url, String about){
        this.nickname = nickname;
        this.github_id = githubId;
        this.introduce = about;
        this.picture = fileName;
        this.picture_real = url;
    }
}
