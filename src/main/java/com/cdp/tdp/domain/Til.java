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
    private String tilContent;

    @Column(nullable = false)
    private boolean tilView;

    @Column(nullable = false)
    private int tilLike;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy="til", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy="til", cascade = CascadeType.REMOVE)
    private List<Tag> tags;

    public Til(Long id, String tilTitle, String tilContent, boolean tilView, int tilLike, User user, List<Comment> comments, List<Tag> tags) {
        this.id = id;
        this.tilTitle = tilTitle;
        this.tilContent = tilContent;
        this.tilView = tilView;
        this.tilLike = tilLike;
        this.user = user;
        this.comments = comments;
        this.tags=tags;
    }

    @Builder
    public Til(TilRequestDto tilRequestDto, User user) {
        this.tilTitle = tilRequestDto.getTilTitle();
        this.tilContent = tilRequestDto.getTilContent();
        this.tilView = tilRequestDto.isTilView();
        this.user = user;
    }


    public void updateMyTil(TilRequestDto tilRequestDto){
        this.tilTitle= tilRequestDto.getTilTitle();
        this.tilContent=tilRequestDto.getTilContent();
    }


    public void updateMyTilView(){
        tilView = !(tilView);
    }

}
