package org.vnsemkin.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vnsemkin.taskmanagementsystem.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
