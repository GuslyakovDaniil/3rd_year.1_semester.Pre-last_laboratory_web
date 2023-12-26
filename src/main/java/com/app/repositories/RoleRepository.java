package com.app.repositories;

import com.app.enums.Role;
import com.app.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findRoleByName(Role role);
}
