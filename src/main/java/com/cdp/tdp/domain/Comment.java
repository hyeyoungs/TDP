package com.cdp.tdp.domain;
import com.cdp.tdp.dto.CommentRequestDto;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 대신 생성해줍니다.
@Entity // 테이블임을 나타냅니다.
public class Comment {

    @Id // ID 값, Primary Key로 사용하겠다는 뜻입니다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가 명령입니다.
    private Long id;

    @Column(nullable = false) // 컬럼 값이고 반드시 값이 존재해야 함을 나타냅니다.
    private String til_comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "til_id", nullable = false)
    private Til til;

    public String getTil_comment() {
        return til_comment;
    }

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user.getId();
    }

    public Comment(CommentRequestDto requestDto, User user, Til til) {
        this.til_comment = requestDto.getTil_comment();
        this.user = user;
        this.til = til;

    }


}
