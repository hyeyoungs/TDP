package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Til;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface TilRepository extends JpaRepository<Til,Long> {

    Optional<Til> findById(Long til_id);
    @Transactional
    @Modifying
    @Query("update Til til set til.til_like = til.til_like + 1 where til.id = :id")
    int plusLike(Long id);
    @Transactional
    @Modifying
    @Query("update Til til set til.til_like = til.til_like - 1 where til.id = :id")
    int minusLike(Long id);


}



