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

    @Transactional
    public Til addLike(User user, Long til_id) {

        Til til = tilRepository.findById(til_id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        if(!isNotAlreadyLike(user, til)) {
            likeRepository.save(new Likes(til,user));
            int like_num=til.getTilLike()+1;
            til.setTilLike(like_num);
            tilRepository.save(til);
            return til;

        }
        return til;

    }

    public boolean isNotAlreadyLike(User user,Til til) {
        return likeRepository.findByTilAndUser(til,user).isPresent();
    }

    @Transactional
    public Til CancelLike(User user, Long til_id) {
        Til til = tilRepository.findById(til_id).get();
        int like_num=til.getTilLike();

        if(isNotAlreadyLike(user, til) && like_num>0) {
            like_num=like_num-1;
            til.setTilLike(like_num);
            tilRepository.save(til);
            likeRepository.deleteByTilAndUser(til,user);
            return til;
        }
        return til;

    }



}