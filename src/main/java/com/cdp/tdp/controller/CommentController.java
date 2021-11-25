package com.cdp.tdp.controller;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.dto.CommentRequestDto;
import com.cdp.tdp.security.UserDetailsImpl;
import com.cdp.tdp.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class CommentController {
    // 멤버 변수 선언
    private final CommentService commentService;

    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping("/api/comments")
    public List<Comment> getComment() throws SQLException {
        List<Comment> comments = commentService.getComment();
        // 응답 보내기
        return comments;
    }


    @PostMapping("/api/comment")
    public Comment createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        Comment comment = commentService.createComment(requestDto, userDetails.getUser().getId(), requestDto.getTil_id());
        return comment;
    }

    @DeleteMapping("/api/comments/{id}")
    public Long deleteComment(@PathVariable Long id) throws SQLException {
        commentService.deleteComment(id);
        return id;
    }


}
