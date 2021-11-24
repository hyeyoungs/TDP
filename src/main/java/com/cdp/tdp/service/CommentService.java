package com.cdp.tdp.service;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.dto.CommentRequestDto;
import com.cdp.tdp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Service
public class CommentService {
    // 멤버 변수 선언
    private final CommentRepository commentRepository;

    // 생성자: ProductService() 가 생성될 때 호출됨
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        // 멤버 변수 생성
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComment() {
        // 멤버 변수 사용
        return commentRepository.findAll();
    }


    public Comment createComment(CommentRequestDto requestDto) {
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
