package com.cdp.tdp.domain;

import com.cdp.tdp.dto.TilRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Til extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String til_title;

    @Column(nullable = false)
    private String til_content;


    @Column(nullable = false)
    private boolean til_view;

    @Column(nullable = false)
    private Long til_like;

//
//    @JoinColumn(nullable = false)
//    @OneToMany
//    private Til user_id;


    @Builder
    public Til(TilRequestDto tilRequestDto) {
        this.til_title = tilRequestDto.getTil_title();
        this.til_content = tilRequestDto.getTil_content();
        this.til_view = tilRequestDto.isTil_view();
        this.til_like =tilRequestDto.getTil_like();
    }

    public void updateMyTil(TilRequestDto tilRequestDto){
        this.til_title= tilRequestDto.getTil_title();
        this.til_content=tilRequestDto.getTil_content();
    }

}
