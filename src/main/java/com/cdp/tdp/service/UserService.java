package com.cdp.tdp.service;

import com.cdp.tdp.domain.Const;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.Trans;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.*;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import com.cdp.tdp.security.kakao.KakaoOAuth2;
import com.cdp.tdp.security.kakao.KakaoUserInfo;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TilRepository tilRepository;
    private final FileService fileService;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public void registerUser(SignupRequestDto requestDto) {
        String signId = requestDto.getUsername();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(signId);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        requestDto.setPicture("profile_placeholder.png");
        requestDto.setPicture_real("./profile_pics/profile_placeholder.png");
        String signPassword = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        String githubId = requestDto.getGithub_id();
        String introduce = requestDto.getIntroduce();
        String picture = requestDto.getPicture();
        String picture_real = requestDto.getPicture_real();

        User user = new User(signId, signPassword, nickname, githubId, introduce, picture, picture_real);
        userRepository.save(user);
    }

    public String kakaoLogin(String token) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(token);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = userInfo.getEmail();
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;
        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            // ROLE = 사용자
            kakaoUser = new User(username, encodedPassword, nickname, kakaoId);
            userRepository.save(kakaoUser);
        }
        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return username;
    }

    private final HttpSession httpSession;

    @Autowired
    public HttpCallService httpCallService;

    public String sendmessage(String token) {
        String KAKAO_API_HOST = "https://kapi.kakao.com";
        String uri = KAKAO_API_HOST + "/v2/api/talk/memo/send";
        return httpCallService.CallwithToken(Const.POST, uri, token, Trans.default_msg_param);
    }

    public List<UserTilCountDto> getAllUser() {
        List<User> user_list = userRepository.findAll(); // 모든 user 를 리스트에 담음
        int tilCount;
        List<UserTilCountDto> CountTilList = new ArrayList<>();

        for (User user : user_list) { //모든 user 조회
            // user의 til갯수 가져오기
            String nickname = user.getNickname();
            tilCount = TilCount(user);
            UserTilCountDto userTilCountDto = new UserTilCountDto();
            userTilCountDto.setTilCount(tilCount);
            userTilCountDto.setNickname(nickname);
            CountTilList.add(userTilCountDto);
        }
        CountTilList.sort(new Comparator<UserTilCountDto>() {
            @Override
            public int compare(UserTilCountDto o1, UserTilCountDto o2) {
                if (o1.getTilCount() < o2.getTilCount()) {
                    return 1;
                } else if (o1.getTilCount() > o2.getTilCount()) {
                    return -1;
                }
                return 0;
            }
        });
        return CountTilList;

    }

    public int TilCount(User user) {
        List<Til> user_tils = user.getTil_list();// 모든 user 를 리스트에 담음
        return user_tils.size();
    }

    @Timed
    @Transactional(timeout = 15)
    public void updateUser(User user, String nickname, String githubId, MultipartFile imageFile, String about) {
        UserUpdateDto userUpdateDto = new UserUpdateDto();

        userUpdateDto.setNickname(nickname);
        userUpdateDto.setGithub_id(githubId);
        userUpdateDto.setIntroduce(about);

        if (imageFile == null) {
            userUpdateDto.setPicture(user.getPicture());
            userUpdateDto.setPicture_real(user.getPicture_real());
        } else {
            userUpdateDto.setPicture(imageFile.getOriginalFilename());
            userUpdateDto.setPicture_real(fileService.uploadImage(imageFile));
        }

        user.updateUser(userUpdateDto);
        userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such user"));
    }


}
