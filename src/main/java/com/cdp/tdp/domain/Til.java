package com.cdp.tdp.domain;

import com.cdp.tdp.dto.TilRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String til_title;

    @Column(nullable = false)
    private String til_content;

    @Column(nullable = false)
    private boolean til_view;

    @Column(nullable = false)
    private int til_like;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy="til")
    private List<Comment> comments;

    public Til(Long id, String til_title, String til_content, boolean til_view, Long til_like, User user, List<Comment> comments) {
        this.id = id;
        this.til_title = til_title;
        this.til_content = til_content;
        this.til_view = til_view;
        this.til_like = til_like;
        this.user = user;
        this.comments = comments;
    }

    @Builder
    public Til(TilRequestDto tilRequestDto, User user) {
        this.til_title = tilRequestDto.getTil_title();
        this.til_content = tilRequestDto.getTil_content();
        this.til_view = tilRequestDto.isTil_view();
        this.til_like=tilRequestDto.getTil_like();
        this.user = user;
    }


    public void updateMyTil(TilRequestDto tilRequestDto){
        this.til_title= tilRequestDto.getTil_title();
        this.til_content=tilRequestDto.getTil_content();
    }


}
