package com.cdp.tdp.security;

import com.cdp.tdp.domain.User;
import com.cdp.tdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String signId) throws IllegalArgumentException {
        User user = userRepository.findBySignId(signId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        return new UserDetailsImpl(user);
    }
}
