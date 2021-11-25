package com.cdp.tdp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자 만든다.
@Entity // DB 테이블 역할
public class User extends Timestamped {

    public User(String signId, String signPassword, String nickname, String githubId, String introduce) {
        this.username = signId;
        this.password = signPassword;
        this.nickname = nickname;
        this.github_id = githubId;
        this.introduce = introduce;
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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Til> tils;


    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Comment> comments;
}
