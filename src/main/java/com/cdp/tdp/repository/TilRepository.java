package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TilRepository extends JpaRepository<Til, Long> {
    // get all til
    List<Til> findByTilViewOrderByIdDesc(boolean tilView);
    // get all til using pagination
    Page<Til> findByTilViewOrUserIdOrderByIdDesc(boolean tilView, Long userId, Pageable pageable);
    // get one til by id
    Optional<Til> findById(Long til_id);
    // get user til
    List<Til> findByUser(User user);
    // get my til
    List<Til> findByUserOrderByIdDesc(User user);
    // get search output
    Page<Til> findByUserAndTilViewOrUserAndUserIdOrderByIdDesc(User user, boolean tilView, User user2, Long userId, Pageable pageable);
    Page<Til> findByTilTitleAndTilViewOrTilTitleAndUserIdOrderByIdDesc(String tilTitle, boolean tilView, String tilTitle2, Long userId, Pageable pageable);
    Page<Til> findByTagsNameAndTilViewOrTagsNameAndUserIdOrderByIdDesc(String keyword, boolean tilView, String keyword2, Long userId, Pageable pageable);
    // get til by comment
    Optional<Til> findByCommentsId(Long commentId);
}

