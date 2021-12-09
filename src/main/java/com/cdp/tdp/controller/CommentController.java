package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.CommentRequestDto;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.CommentService;
import com.cdp.tdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class CommentController {
    // 멤버 변수 선언
    private final CommentService commentService;
    private final UserService userService;


    @GetMapping("/til/{id}/comments")
    public List getComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        List<Comment> comments = commentService.getComment(id);
        User user = userService.getUser(userDetails.getUser().getId());
        List<Object> list = new ArrayList<>();
        list.add(user);
        list.addAll(comments);

        // 응답 보내기
        return list;
    }

    @PostMapping("/til/comment")
    public Comment createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        Comment comment = commentService.createComment(requestDto, userDetails.getUser().getId());
        return comment;
    }

    @DeleteMapping("/til/comments/{id}")
    public Long deleteComment(@PathVariable Long id) throws SQLException {
        commentService.deleteComment(id);
        return id;
    }


}
