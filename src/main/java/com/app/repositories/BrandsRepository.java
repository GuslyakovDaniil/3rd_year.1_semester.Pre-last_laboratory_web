package com.app.repositories;

import com.app.models.Brands;
import com.app.models.Models;
import com.app.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

public interface BrandsRepository extends JpaRepository<Brands,Integer>{
    Optional<Brands> findByName(String name);
    @Modifying
    @Transactional
    void deleteByName(String name);
}
