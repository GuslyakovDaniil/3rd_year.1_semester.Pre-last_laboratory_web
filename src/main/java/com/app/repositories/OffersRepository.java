package com.app.repositories;

import com.app.models.Models;
import com.app.models.Offers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OffersRepository extends JpaRepository<Offers, Integer> {
    Optional<Offers> findByDescription(String description);

    List<Offers> findByOrderByYearDesc(PageRequest pageRequest);
    @Modifying
    @Transactional
    void deleteByDescription(String description);
}