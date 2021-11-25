package com.cdp.tdp.repository;


import com.cdp.tdp.domain.Likes;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByTilAndUser(Til til, User user);
    Optional<Likes> deleteByTilAndUser(Til til,User user);


    int countByTilId(Long til_id);
}