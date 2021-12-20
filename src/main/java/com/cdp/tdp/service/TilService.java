package com.cdp.tdp.service;

import com.cdp.tdp.domain.Tag;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.repository.TagRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TilService {

    private final TilRepository tilRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    public List<Til> getAllTil() {
        return tilRepository.findByTilViewOrderByIdDesc(true);
    }

    public Page<Til> getTilList(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, size);
        return tilRepository.findByTilViewOrUserIdOrderByIdDesc(true, user.getId(), pageable);
    }

    public Til createTil(TilRequestDto tilRequestDto, Long id) throws SQLException {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such user"));

        Til til = new Til(tilRequestDto, user);
        tilRepository.save(til);

        if(!(tilRequestDto.getTags().isEmpty())) {
            String[] tagArray = tilRequestDto.getTags().split("\\s*,\\s*");
            List<Tag> tagList = new ArrayList<>();
            for (String s : tagArray) {
                Tag tag = new Tag(s, til);
                tagList.add(tag);
            }
            tagRepository.saveAll(tagList);
        }


        return til;
    }

    public Til getTil(Long id) {
        return tilRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
    }
    public List<Til> getTil_name(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("해당 유저가 존재하지 않습니다"));

        return tilRepository.findByUser(user);
    }

    public void deleteTil(Long id) {
        tilRepository.deleteById(id);
    }

    @Transactional
    public void updateTil(Long id, TilRequestDto tilRequestDto)throws SQLException{
        Til til = tilRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        til.updateMyTil(tilRequestDto);
    }

    public List<Til> getUserTil(User user){
        return tilRepository.findByUserOrderByIdDesc(user);
    }

    public void updateTilView(Long id){
        Til til = tilRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        til.updateMyTilView();
        tilRepository.save(til);
    }

    public Page<Til> searchTil(int page, int size, String keyword, String setting, User user) {
        Pageable pageable = PageRequest.of(page, size);

        if(setting.equals("제목")) {  return tilRepository.findByTilTitleAndTilViewOrTilTitleAndUserIdOrderByIdDesc(keyword, true, keyword, user.getId(), pageable);    }
        else if(setting.equals("작성자")) {
            User writer = userRepository.findByUsername(keyword)
                    .orElseThrow(() -> new UsernameNotFoundException("로그인 오류"));
            return tilRepository.findByUserAndTilViewOrUserAndUserIdOrderByIdDesc(writer, true, writer, user.getId(), pageable);
        }
        // 태그
        else {  return tilRepository.findByTagsNameAndTilViewOrTagsNameAndUserIdOrderByIdDesc(keyword, true, keyword, user.getId(), pageable);    }
    }

}
