package com.cdp.tdp.domain;

import com.cdp.tdp.dto.TilRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class Til extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "til_id")
    private Long id;

    @Column(nullable = false)
    private String tilTitle;

    @Column(nullable = false)
    private String til_content;

    @Column(nullable = false)
    private boolean til_view;

    @Column(nullable = false)
    private int til_like;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy="til")
    private List<Comment> comments;

    @OneToMany(mappedBy="til")
    private List<Tag> tags;

    public Til(Long id, String tilTitle, String til_content, boolean til_view, int til_like, User user, List<Comment> comments,List<Tag> tags) {
        this.id = id;
        this.tilTitle = tilTitle;
        this.til_content = til_content;
        this.til_view = til_view;
        this.til_like = til_like;
        this.user = user;
        this.comments = comments;
        this.tags=tags;
    }

    @Builder
    public Til(TilRequestDto tilRequestDto, User user) {
        this.tilTitle = tilRequestDto.getTilTitle();
        this.til_content = tilRequestDto.getTil_content();
        this.til_view = tilRequestDto.isTil_view();
        this.user = user;
    }


    public void updateMyTil(TilRequestDto tilRequestDto){
        this.tilTitle= tilRequestDto.getTilTitle();
        this.til_content=tilRequestDto.getTil_content();
    }


    public void updateMyTilView(){
        til_view = !(til_view);
    }

}
