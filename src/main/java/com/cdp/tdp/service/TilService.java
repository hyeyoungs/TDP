package com.cdp.tdp.service;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.TilRequestDto;
import com.cdp.tdp.repository.CommentRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TilService {
    private final TilRepository tilRepository;
    private final UserRepository userRepository;

    public List<Til> getAllTil() {
        return tilRepository.findAll();
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
    public Til updateTil(Long id , TilRequestDto tilRequestDto)throws SQLException{
        Til til = tilRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        til.updateMyTil(tilRequestDto);
        return til;
    }
}
