package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Tag;
import com.cdp.tdp.domain.Til;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Til> findAllByName(String name);
}