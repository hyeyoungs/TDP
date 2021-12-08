package com.cdp.tdp.chat;

import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Objects;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("message:" + message);
        System.out.println("헤더 : " + message.getHeaders());
        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")));
            log.info(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).substring(7));
        }
        return message;
    }
}
