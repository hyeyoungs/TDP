package com.cdp.tdp.chat;

import com.cdp.tdp.domain.User;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.UserService;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.Map;

@Slf4j
public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);


    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {


        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUs")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info(user.getUsername());

            log.info(String.valueOf(user));
            String id = String.valueOf(user.getId());
            log.info(id);
            LOG.info("User with ID '{}' opened the page", id);
            return new UserPrincipal(id);
        } else {
            String user = "anonymousUser";
            log.info(user);
        }


        return new UserPrincipal("");
    }
}
