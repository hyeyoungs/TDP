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
    Page<Til> findByTilViewOrderByIdDesc(boolean tilView, Pageable pageable);

    // get one til by id
    Optional<Til> findById(Long til_id);

    // like update
    @Transactional
    @Modifying
    @Query("update Til til set til.tilLike = til.tilLike + 1 where til.id = :id")
    int plusLike(Long id);
    @Transactional
    @Modifying
    @Query("update Til til set til.tilLike = til.tilLike - 1 where til.id = :id")
    int minusLike(Long id);

    // get my til
    @Query("select til from Til til order by til.id DESC")
    List<Til> findByUser(User user);

    // get search output
    @Query("select til from Til til where til.tilView = true and til.user = :user order by til.id DESC")
    Page<Til> findAllByUser(User user, Pageable pageable);
    @Query("select til from Til til where til.tilView = true and til.tilTitle = :til_title order by til.id DESC")
    Page<Til> findAllByTilTitle(String til_title, Pageable pageable);
    @Query("select til from Til til where til.tilView = true and til.tags= :keyword order by til.id DESC")
    Page<Til> findAllByTagsName(String keyword, Pageable pageable);
//    findAllByTil_viewOrUserId(boolean true, Long id)
}

