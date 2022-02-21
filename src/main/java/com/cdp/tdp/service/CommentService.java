package com.cdp.tdp.service;

import com.cdp.tdp.domain.Comment;
import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import com.cdp.tdp.dto.CommentRequestDto;
import com.cdp.tdp.repository.CommentRepository;
import com.cdp.tdp.repository.TilRepository;
import com.cdp.tdp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TilRepository tilRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, TilRepository tilRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tilRepository = tilRepository;
    }

    public List<Comment> getComment(Long id) {
        return commentRepository.findByTilId(id);
    }

    public Comment createComment(CommentRequestDto requestDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such user"));
        Til til = tilRepository.findById(requestDto.getIdx()).orElseThrow(() -> new IllegalArgumentException("no such til"));

        Comment comment = new Comment(requestDto, user, til);
        commentRepository.save(comment);
        int comment_num=til.getNum_comment()+1;
        til.setNum_comment(comment_num);
        tilRepository.save(til);
        return comment;
    }

    public void deleteComment(Long id)  {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no such comment"));
        Til til=comment.getTil();

        int comment_num=til.getNum_comment()-1;
        til.setNum_comment(comment_num);
        tilRepository.save(til);
        commentRepository.deleteById(id);
    }


}