package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Tag;
import com.cdp.tdp.domain.Til;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Til> findAllByName(String name);
    List<Tag> findByNameAndTil(String name, Til til);
}