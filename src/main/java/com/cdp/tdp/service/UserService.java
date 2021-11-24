package com.cdp.tdp.service;

import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.SignupRequestDto;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        User user = new User(signId, signPassword, nickname, githubId, introduce);
        userRepository.save(user);
        return user;
    }

}
