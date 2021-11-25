package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.JwtResponse;
import com.cdp.tdp.dto.SignupRequestDto;
import com.cdp.tdp.dto.UserDto;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.UserService;
import com.cdp.tdp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.cdp.tdp.util.JwtTokenUtil;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/user")
    public User getMyUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return userService.getMyUser(user);
    }
}
