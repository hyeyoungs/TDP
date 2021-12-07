package com.cdp.tdp.service;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.Tag;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.repository.CommentRepository;
import com.cdp.tdp.repository.TagRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TilService {
    private final TilRepository tilRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public List<Til> getAllTil() {
        return tilRepository.findAllByOrderByIdDesc();
    }

    public Til createTil(TilRequestDto tilRequestDto, Long id) throws SQLException {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such user"));

        Til til = new Til(tilRequestDto, user);
        //System.out.print(til);
        tilRepository.save(til);

        String[] tagArray = tilRequestDto.getTags().split("\\s*,\\s*");
        List<Tag> tagList = new ArrayList<>();
        for (int i = 0; i < tagArray.length; i++) {
            Tag tag=new Tag(tagArray[i],til);
            tagList.add(tag);
        }
        tagRepository.saveAll(tagList);
        return til;
    }

    public Til getTil(Long id) {
        return tilRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
    }



    public void deleteTil(Long id) {
        tilRepository.deleteById(id);
    }

    @Transactional
    public Til updateTil(Long id, TilRequestDto tilRequestDto)throws SQLException{
        Til til = tilRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        til.updateMyTil(tilRequestDto);
        return til;
    }

    public List<Til> getUserTil(User user){
        return tilRepository.findByUser(user);
    }

    public void updateTilView(Long id){
        Til til = tilRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        til.updateMyTilView();
        tilRepository.save(til);
    }


    public List<Til> SearchTil(String keyword,String setting)
    {
        if(setting.equals("제목"))
        {

            return tilRepository.findAllByTilTitle(keyword);
        }
        else if(setting.equals("작성자"))
        {

            User user = userRepository.findByUsername(keyword)
                    .orElseThrow(() -> new UsernameNotFoundException("로그인 오류"));
            return tilRepository.findAllByUser(user);

        }

        else // 태그
        {

            return tilRepository.findAllByTagsName(keyword);


        }
    }

}
