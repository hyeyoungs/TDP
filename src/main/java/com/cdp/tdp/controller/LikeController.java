package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.repository.LikeRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

            Til til = likeService.addLike(userDetails.getUser(), til_id);
            return til;



    }

    @GetMapping("/til/like/{til_id}")
    //사용자가 이 글을 좋아요 눌렀는지 안눌렀는지 판별하는 함수
    public boolean getLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id) {

        Til til = tilRepository.findById(til_id).get();
        boolean result = false;

        if (userDetails != null) {
            if (likeRepository.findByTilAndUser(til, userDetails.getUser()).isPresent()) {
                result = true; //이미 좋아요 누른 상태
            }

            return result;
        }
        return result;
    }
    //좋아요 취소
    @DeleteMapping("/til/dislike/{til_id}")
    public Til CancelLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id)
    {

        Til til = likeService.CancelLike(userDetails.getUser(), til_id);
        return til;
    }
}