package com.cdp.tdp.service;


import com.cdp.tdp.domain.Likes;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final TilRepository tilRepository;
    private final LikeRepository likeRepository;
    private final TilService tilService;

    public Til addLike(User user, Long til_id) {
        //좋아요를 등록할 til 가져오기
        Til til = tilRepository.findById(til_id).get();

        //중복 좋아요 방지
        if(isNotAlreadyLike(user,til)==false) {
            likeRepository.save(new Likes(til,user));
            int count_num=likeRepository.countByTil(til);
            til.setTil_like(count_num);
            return til;
        }
        return til;

        //좋아요 수 증가 로직
        // 레포지토리에서 til_id로 찾아서 좋아요 갯수 가져옴



    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    public boolean isNotAlreadyLike(User user,Til til) {
        return likeRepository.findByTilAndUser(til,user).isPresent();
    }
    public Til CancelLike(User user, Long til_id) {
        //좋아요를 취소할 til 가져오기
        Til til = tilRepository.findById(til_id).get();

        if(isNotAlreadyLike(user,til)==true) {
            likeRepository.deleteByTilAndUser(til,user);
            int count_num=likeRepository.countByTil(til);
            til.setTil_like(count_num);
            return til;
        }
        return til;

    }

}