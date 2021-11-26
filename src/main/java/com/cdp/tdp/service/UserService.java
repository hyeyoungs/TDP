package com.cdp.tdp.service;

import com.cdp.tdp.controller.UserController;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.*;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
        public User registerUser(SignupRequestDto requestDto) {
            String signId = requestDto.getUsername();
            // 회원 ID 중복 확인
            Optional<User> found = userRepository.findByUsername(signId);
            if (found.isPresent()) {
                throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
            }

            String signPassword = passwordEncoder.encode(requestDto.getPassword());
            String nickname = requestDto.getNickname();
            String githubId = requestDto.getGithub_id();
            String introduce = requestDto.getIntroduce();
            String picture=requestDto.getPicture();
            String picture_real=requestDto.getPicture_real();

            User user = new User(signId, signPassword, nickname, githubId, introduce,picture,picture_real);
            userRepository.save(user);
        return user;
    }

    public User getMyUser(User user) {
        return userRepository.findById(user);
    }
    public List<User> getAllUser(UserTilCountDto userTilCountDto){
        String signId = userTilCountDto.getUsername();
        Long count = userTilCountDto.getTil_count();
        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user , UserUpdateDto userUpdateDto)throws SQLException {

        user.updateUser(userUpdateDto);
        userRepository.save(user);
        return user;
    }

}
