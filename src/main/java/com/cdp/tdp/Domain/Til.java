package com.cdp.tdp.Domain;

import com.cdp.tdp.Dto.TilRequestDto;
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

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    private String til_title;

    @Column(nullable = false)
    private String til_content;

    @Column(nullable = false)
    private int like;

    @Column(nullable = false)
    private String til_user;

    @Column(nullable = false)
    private boolean til_view;

    @OneToMany
    private List<Comment> commentList;

    @OneToOne
    private List<Like> likeList;

    @Builder
    public Til(TilRequestDto tilRequestDto) {
        this.til_title = tilRequestDto.getTil_title();
        this.til_content = tilRequestDto.getTil_content();
        this.til_user = tilRequestDto.getTil_user();
        this.til_view = tilRequestDto.isTil_view();

    }


}
