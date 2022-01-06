package com.cdp.tdp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.transaction.Transactional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TilRequestDto {
    //제목,본문,작성자,보기권한
    private String tilTitle;
    private String tilContent;
    private boolean tilView;
    private String user_id;
    private String tags;
}