//package com.cdp.tdp.chat;
//
//import com.cdp.tdp.util.JwtTokenUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//
//@Component
//@RequiredArgsConstructor
//public class StompHandler implements ChannelInterceptor {
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        System.out.println("요청메시지:"+accessor.getCommand() );
//        System.out.println("message:" + message);
//        System.out.println("헤더 : " + message.getHeaders());
//        System.out.println("토큰" + accessor.getNativeHeader("Authorization"));
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            System.out.println(accessor.getFirstNativeHeader("Authorization"));
//            jwtTokenUtil.validateToken2(Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")));
//        }
//        return message;
//    }
//}
