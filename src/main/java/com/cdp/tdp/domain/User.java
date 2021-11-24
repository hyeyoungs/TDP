package com.cdp.tdp.domain;
import lombok.*;
import javax.persistence.*;

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
    private Long id;

    // 반드시 값을 가지도록 한다.
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String github_id;

    private String introduce;

}
