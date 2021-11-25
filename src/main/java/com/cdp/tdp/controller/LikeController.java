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
    public ResponseEntity<String> addLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id) {

        boolean result = false;

        if (userDetails != null) {
            result = likeService.addLike(userDetails.getUser(), til_id);
        }

        return result ?
                new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/til/like/{til_id}")
    public boolean getLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id) {

        Til til = tilRepository.findById(til_id).get();
        boolean result = false;

        if (userDetails != null) {
            if (likeRepository.findByTilAndUser(til, userDetails.getUser())!=null) {
                result = true; //이미 좋아요 누른 상태
            }

            return result;
        }


        return false;

    }
    //좋아요 취소
    @DeleteMapping("/til/dislike/{til_id}")
    public boolean CancelLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long til_id) {
        Til til = tilRepository.findById(til_id).get();
        likeRepository.deleteByTilAndUser(til, userDetails.getUser()); //좋아요 id로 취소
        //좋아요 수 감소 로직
        return true;
    }
}