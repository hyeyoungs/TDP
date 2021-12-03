package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTilId(Long til_id);
}
