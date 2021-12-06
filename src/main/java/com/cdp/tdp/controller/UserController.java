package com.cdp.tdp.controller;

import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.*;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.security.UserDetailsServiceImpl;
import com.cdp.tdp.service.UserService;
import com.cdp.tdp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserDto userDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 실패");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @PostMapping(value = "/login/kakao")
    public ResponseEntity<?> createAuthenticationTokenByKakao(@RequestBody SocialLoginDto socialLoginDto) throws Exception {
        //api 인증을 통해 얻어온 code값 받아오기
        String username = userService.kakaoLogin(socialLoginDto.getToken());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        userService.registerUser(userDto);
        return ResponseEntity.ok("ok");
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

    @PutMapping("/user/update")
    public void updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserUpdateDto userUpdateDto) throws SQLException{
        userService.updateUser(userDetails.getUser(), userUpdateDto);
    }




}
