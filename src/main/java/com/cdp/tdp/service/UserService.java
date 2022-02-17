package com.cdp.tdp.service;

import com.cdp.tdp.domain.Const;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.Trans;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.*;
import com.cdp.tdp.repository.UserRepository;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.security.UserDetailsServiceImpl;
import com.cdp.tdp.security.kakao.KakaoOAuth2;
import com.cdp.tdp.security.kakao.KakaoUserInfo;
import com.cdp.tdp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final KakaoOAuth2 kakaoOAuth2;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";


    @Transactional
    public void registerUser(SignupRequestDto requestDto) {
        String signId = requestDto.getUsername();
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

    public ResponseEntity<?> login(UserDto userDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("로그인 실패");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    public String kakaoLogin(String token) {
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(token);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();
        String username = userInfo.getEmail();
        String password = kakaoId + ADMIN_TOKEN;

        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        if (kakaoUser == null) {
            String encodedPassword = passwordEncoder.encode(password);
            kakaoUser = new User(username, encodedPassword, nickname, kakaoId);
            userRepository.save(kakaoUser);
        }

        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return username;
    }

    @Autowired
    public HttpCallService httpCallService;

    public String sendmessage(String token) {
        String KAKAO_API_HOST = "https://kapi.kakao.com";
        String uri = KAKAO_API_HOST + "/v2/api/talk/memo/send";
        return httpCallService.CallwithToken(Const.POST, uri, token, Trans.default_msg_param);
    }

    public List<UserTilCountDto> getAllUser() {
        List<User> user_list = userRepository.findAll();
        int tilCount;
        List<UserTilCountDto> CountTilList = new ArrayList<>();

        for (User user : user_list) {
            String nickname = user.getNickname();
            String username = user.getUsername();
            tilCount = TilCount(user);
            UserTilCountDto userTilCountDto = new UserTilCountDto();
            userTilCountDto.setTilCount(tilCount);
            userTilCountDto.setNickname(nickname);
            userTilCountDto.setUsername(username);

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
        List<Til> user_tils = user.getTil_list();
        return user_tils.size();
    }


    public void updateUser(User user, String nickname, String githubId, MultipartFile imageFile, String about) throws IOException {
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

    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

}
