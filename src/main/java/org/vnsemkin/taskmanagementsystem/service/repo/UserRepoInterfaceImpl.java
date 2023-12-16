package org.vnsemkin.taskmanagementsystem.service.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.vnsemkin.taskmanagementsystem.entity.User;
import org.vnsemkin.taskmanagementsystem.repository.UserRepository;

import java.util.Optional;

@Repository
public class UserRepoInterfaceImpl implements UserRepoInterface{
    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
