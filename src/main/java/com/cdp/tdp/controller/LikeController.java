package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.repository.LikeRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;
    private final LikeRepository likeRepository;
    private final TilRepository tilRepository;


    @PostMapping("/til/like/{til_id}")
    public Til addLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id) {
            User user=(User) userDetails.getUser();
            Til til = likeService.addLike(user, til_id);
            return til;

    }

    @GetMapping("/til/like/{til_id}")
    public boolean getLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id) {

        Til til = tilRepository.findById(til_id).get();
        User user=(User) userDetails.getUser();
        boolean result = false;


        if (likeRepository.findByTilAndUser(til, user).isPresent()) {
            result = true;
        }

        return result;

    }

    @DeleteMapping("/til/dislike/{til_id}")
    public Til CancelLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id)
    {
        User user=(User) userDetails.getUser();
        Til til = likeService.CancelLike(user, til_id);
        return til;
    }
}