package com.cdp.tdp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String signId;
    private String signPassword;
    private String nickname;
    private String githubId;
    private String introduce;
}
