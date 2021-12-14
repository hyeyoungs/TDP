package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TilRepository extends JpaRepository<Til, Long> {
    // get all til
    List<Til> findByTilViewOrderByIdDesc(boolean tilView);
    // get all til using pagination
    Page<Til> findByTilViewOrUserIdOrderByIdDesc(boolean tilView, Long userId, Pageable pageable);
    // get one til by id
    Optional<Til> findById(Long til_id);

    // get my til
    List<Til> findByUserOrderByIdDesc(User user);

    // get search output
    Page<Til> findByUserOrUserIdOrderByIdDesc(User user, boolean tilView, Long userId, Pageable pageable);
    Page<Til> findByTilTitleOrUserIdOrderByIdDesc(String tilTitle, boolean tilView, Long userId, Pageable pageable);
    Page<Til> findByTagsNameOrUserIdOrderByIdDesc(String keyword, boolean tilView, Long userId, Pageable pageable);
}

