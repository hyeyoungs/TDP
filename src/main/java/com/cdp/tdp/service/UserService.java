package com.cdp.tdp.service;

import com.cdp.tdp.controller.UserController;
import com.cdp.tdp.domain.Const;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.Trans;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.*;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import com.cdp.tdp.security.kakao.KakaoOAuth2;
import com.cdp.tdp.security.kakao.KakaoUserInfo;
import jdk.nashorn.api.scripting.JSObject;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import com.google.gson.JsonParser;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TilRepository tilRepository;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
        public User registerUser(SignupRequestDto requestDto) {
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

            User user = new User(signId, signPassword, nickname, githubId, introduce,picture,picture_real);
            userRepository.save(user);
        return user;
    }

    public String kakaoLogin(String token) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(token);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String email = userInfo.getEmail();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = email;
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


            kakaoUser = new User(username, encodedPassword, nickname,  kakaoId);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        //  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 로 진행됨

        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return username;
    }

    private final HttpSession httpSession;

    @Autowired
    public HttpCallService httpCallService;



    public String sendmessage(String token) {

        String KAKAO_API_HOST="https://kapi.kakao.com";
        String uri = KAKAO_API_HOST + "/v2/api/talk/memo/send";

        log.info(uri);
        log.info(Trans.default_msg_param);

        log.info(httpCallService.CallwithToken(Const.POST, uri, token, Trans.default_msg_param));
        return httpCallService.CallwithToken(Const.POST, uri, token, Trans.default_msg_param);
    }




    public List<UserTilCountDto> getAllUser(){
        List<User> user_list= userRepository.findAll(); // 모든 user 를 리스트에 담음
        int til_count;
        List<UserTilCountDto> CountTilList=new ArrayList<>();

        for(User user : user_list) { //모든 user 조회
             // user의 til갯수 가져오기
            String username=user.getUsername();
            til_count=TilCount(user);
            UserTilCountDto userTilCountDto=new UserTilCountDto();
            userTilCountDto.setTil_count(til_count);
            userTilCountDto.setUsername(username);
            CountTilList.add(userTilCountDto);
        }
        CountTilList.sort(new Comparator<UserTilCountDto>() {
            @Override
            public int compare(UserTilCountDto o1, UserTilCountDto o2) {
                if (o1.getTil_count()< o2.getTil_count()){
                    return 1;
                } else if (o1.getTil_count()>o2.getTil_count()){
                    return -1;
                }
                return 0;
            }
        });
        return CountTilList;

    }
    public int TilCount(User user){
        List<Til> user_tils=user.getTil_list();// 모든 user 를 리스트에 담음
        return user_tils.size();
    }

    @Transactional
    public User updateUser(User user , UserUpdateDto userUpdateDto)throws SQLException {

        user.updateUser(userUpdateDto);
        userRepository.save(user);
        return user;
    }

    public User getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such user"));
        return user;
    }


}
