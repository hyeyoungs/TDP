package com.cdp.tdp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@Entity // DB 테이블 역할을 합니다
public class Likes extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name="TIL_ID",nullable = false)
    private Til til;

    @ManyToOne
    @JoinColumn(name="USER_ID",nullable = false)
    private User user;

    public Likes(Til til, User user) {
        this.til = til;
        this.user = user;

    }
}