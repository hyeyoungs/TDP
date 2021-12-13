package com.cdp.tdp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TilRequestDto {
    //제목,본문,작성자,보기권한
    private String tilTitle;
    private String tilContent;
    private boolean tilView;
    private String user_id;
    private String tags;
}