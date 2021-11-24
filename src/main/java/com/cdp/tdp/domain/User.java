package com.cdp.tdp.domain;

import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor // 기본 생성자 만든다.
@Entity // DB 테이블 역할
public class User extends Timestamped {

    public User(String signId, String signPassword, String nickname, String githubId, String introduce) {
        this.signId = signId;
        this.signPassword = signPassword;
        this.nickname = nickname;
        this.githubId = githubId;
        this.introduce = introduce;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    // 반드시 값을 가지도록 한다.
    @Column(nullable = false)
    private String signId;

    @Column(nullable = false)
    private String signPassword;

    @Column(nullable = false)
    private String nickname;

    private String githubId;

    private String introduce;

}
