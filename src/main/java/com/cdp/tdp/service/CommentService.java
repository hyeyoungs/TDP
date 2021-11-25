package com.cdp.tdp.service;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.CommentRequestDto;
import com.cdp.tdp.repository.CommentRepository;
import com.cdp.tdp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {
    // 멤버 변수 선언
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 생성자: ProductService() 가 생성될 때 호출됨
    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        // 멤버 변수 생성
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getComment() {
        // 멤버 변수 사용
        return commentRepository.findAll();
    }

    public Comment createComment(CommentRequestDto requestDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such data"));
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
        return comment;
    }

    public Long deleteComment(Long id)  {
            commentRepository.deleteById(id);
            return id;
    }


}
