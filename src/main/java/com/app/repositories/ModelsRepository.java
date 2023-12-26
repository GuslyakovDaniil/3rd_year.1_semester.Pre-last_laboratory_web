package com.app.repositories;

import com.app.dtos.AddModelsDto;
import com.app.enums.Category;
import com.app.models.Models;
import com.app.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ModelsRepository extends JpaRepository<Models, Integer>{
    Optional<Models> findByName(String name);
    List<Models> findByCategory(Category category);
    @Modifying
    @Transactional
    void deleteByName(String name);

}
