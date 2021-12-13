package com.cdp.tdp.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UserUpdateRequestDto {
    private String nickname;
    private String github_id;
    private MultipartFile file;
    private String about;
}
