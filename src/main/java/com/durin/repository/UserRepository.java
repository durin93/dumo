package com.durin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durin.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

	Optional<User> findByOauthId(String oauthId);

}
