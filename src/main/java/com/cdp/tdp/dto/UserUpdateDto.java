package com.cdp.tdp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateDto {

    private String nickname;

    private String github_id;

    private String introduce;

    private String picture;

    private String picture_real;

}
