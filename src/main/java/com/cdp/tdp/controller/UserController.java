package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.Ranker;
import com.cdp.tdp.domain.Til;
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

    @PostMapping(value = "/signup")
    public ResponseEntity createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        userService.registerUser(userDto);
        return ResponseEntity.ok("ok");
    }

    @GetMapping(value = "/user")
    public Optional<User> readUser(@AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        String username = userDetails.getUsername();
        return userService.getMyUser(username);
    }

    @GetMapping("til/ranker")
    public List<User> getAllUser(){
        User user = (User) userService.getAllUser();
        return userService.getAllUser();
    }
//    public Comment createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
//        Comment comment = commentService.createComment(requestDto, userDetails.getUser().getId(), requestDto.getTil_id());
//        return comment;
    @PutMapping("/user/update")
    public void updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserUpdateDto userUpdateDto) throws SQLException{
        User user = (User) userDetails.getUser();
        userService.updateUser(user, userUpdateDto);

    }
}
