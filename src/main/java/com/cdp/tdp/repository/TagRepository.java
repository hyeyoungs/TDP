package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}