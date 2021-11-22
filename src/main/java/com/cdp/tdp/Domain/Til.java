package com.cdp.tdp.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Til extends Timestamped{

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String til_title;

    @Column(nullable = false)
    private String til_content;

    @Column(nullable = false)
    private String til_user;

    @Column(nullable = false)
    private int like;

    @OneToMany
    private List<Comment> commentList;

    @OneToOne
    private List<Like> likeList;

}
