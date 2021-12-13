package com.cdp.tdp.service;


import com.cdp.tdp.domain.Likes;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final TilRepository tilRepository;
    private final LikeRepository likeRepository;
    private final TilService tilService;

    @Transactional
    public Til addLike(User user, Long til_id) {
        //좋아요를 등록할 til 가져오기

        Til til = tilRepository.findById(til_id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        //중복 좋아요 방지
        if(!isNotAlreadyLike(user, til)) {
            likeRepository.save(new Likes(til,user));
            int like_num=til.getTilLike()+1;
            til.setTilLike(like_num);
            tilRepository.save(til);
            return til;

        }
        return til;

    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    public boolean isNotAlreadyLike(User user,Til til) {
        return likeRepository.findByTilAndUser(til,user).isPresent();
    }

    @Transactional
    public Til CancelLike(User user, Long til_id) {
        //좋아요를 취소할 til 가져오기
        Til til = tilRepository.findById(til_id).get();

        if(isNotAlreadyLike(user, til)) {
            int like_num=til.getTilLike()-1;
            til.setTilLike(like_num);
            tilRepository.save(til);
            return til;
        }
        return til;

    }



}