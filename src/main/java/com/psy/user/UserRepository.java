package com.psy.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAuth, Integer> {
    Optional<UserAuth> findByEmail(String username);
}
