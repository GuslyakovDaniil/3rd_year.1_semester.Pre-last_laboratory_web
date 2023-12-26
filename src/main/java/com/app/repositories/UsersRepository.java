package com.app.repositories;

import com.app.models.Models;
import com.app.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    @Modifying
    @Transactional
    void deleteByEmail(String email);
}
