package com.cdp.tdp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@Entity // DB 테이블 역할을 합니다
public class Likes extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    @JoinColumn(name="til_id", nullable = false)
    private Til til;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    public Likes(Til til, User user) {
        this.til = til;
        this.user = user;

    }
}