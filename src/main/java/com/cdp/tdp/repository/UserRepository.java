package com.cdp.tdp.repository;

import com.cdp.tdp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySignId(String signId);
}
