package com.cdp.tdp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TilRequestDto {
    private String tilTitle;
    private String tilContent;
    private boolean tilView;
    private String user_id;
    private String tags;
}