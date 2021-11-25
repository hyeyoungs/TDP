package com.cdp.tdp.repository;

import com.cdp.tdp.domain.Til;
import com.cdp.tdp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TilRepository extends JpaRepository<Til, Long> {

    Optional<Til> findById(Long til_id);
    List<Til> findByUser(User user);
}
