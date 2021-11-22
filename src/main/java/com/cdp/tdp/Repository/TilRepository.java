package com.cdp.tdp.Repository;

import com.cdp.tdp.Domain.Til;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TilRepository extends JpaRepository<Til,Long> {
}
