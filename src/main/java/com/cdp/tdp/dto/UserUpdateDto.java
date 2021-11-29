package com.cdp.tdp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
public class UserUpdateDto {



    private String nickname;

    private String github_id;

    private String introduce;

    private String picture;

//    private String picture_real;

}
