package org.vnsemkin.taskmanagementsystem.service.repo;

import org.springframework.stereotype.Repository;
import org.vnsemkin.taskmanagementsystem.entity.User;

import java.util.Optional;

@Repository
public interface UserRepoInterface {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
