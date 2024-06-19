package com.bzu.project.repository;

import ch.qos.logback.core.subst.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    // Find a token by its value
    Optional<Token> findByToken(String token);

    // Additional custom query methods can be added here if needed
}