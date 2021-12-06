package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Comment;

import com.cdp.tdp.domain.Til;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {


}
