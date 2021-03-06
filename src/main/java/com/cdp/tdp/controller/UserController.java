package com.cdp.tdp.controller;

import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.*;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.security.UserDetailsServiceImpl;
import com.cdp.tdp.service.UserService;
import com.cdp.tdp.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Component
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @RequestMapping(value = "/login/kakao")
    public ResponseEntity<?> createAuthenticationTokenByKakao(@RequestBody SocialLoginDto socialLoginDto) throws Exception {

        String username = userService.kakaoLogin(socialLoginDto.getToken());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @PostMapping("/message")
    public void message(@RequestBody SocialLoginDto socialLoginDto) {

        String token = socialLoginDto.getToken();
        String message= userService.sendmessage(token);

    }

    @PostMapping(value = "/signup")
    public String createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        JSONObject response = new JSONObject();
        try {
            userService.registerUser(userDto);
        } catch (IllegalArgumentException e) {
            response.put("IdExists", Boolean.TRUE);
            return response.toString();
        }
        response.put("IdExists", Boolean.FALSE);
        return response.toString();
    }

    @GetMapping(value = "/user")
    public User readUser(@AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        User user = (User) userDetails.getUser();
        return user;
    }

    @GetMapping("/til/ranker")
    public List<UserTilCountDto> getAllUser(){

        return userService.getAllUser();
    }


    @PutMapping("/user/profile")
    public void updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("github_id") String githubId,
                           @RequestParam(value = "file", required = false) MultipartFile imageFile,
                           @RequestParam("about") String about) throws IOException {
        userService.updateUser(userDetails.getUser(), nickname, githubId, imageFile, about);
    }

    @DeleteMapping("/user")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails.getUser());
    }

}
