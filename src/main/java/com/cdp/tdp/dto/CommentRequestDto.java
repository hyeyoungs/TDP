package com.cdp.tdp.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String til_comment;
    private Long idx;

    public String getTil_comment() {
        return til_comment;
    }
}
