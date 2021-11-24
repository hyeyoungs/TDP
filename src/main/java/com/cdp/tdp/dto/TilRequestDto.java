package com.cdp.tdp.dto;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Setter
@Getter
public class TilRequestDto {
    //제목,본문,작성자,보기권한
    private String til_title;
    private String til_content;
    private boolean til_view;
    private Long til_like;

}
