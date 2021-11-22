package com.cdp.tdp.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class TilRequestDto {
    //제목,본문,작성자,보기권한
    private String til_title;
    private String til_content;
    private String til_user;
    private boolean til_view;
}
