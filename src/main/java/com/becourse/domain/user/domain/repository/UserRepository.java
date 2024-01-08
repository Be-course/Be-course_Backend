package com.becourse.domain.user.domain.repository;

import com.becourse.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId);

    @Query(value = "SELECT * FROM user_entity WHERE o_auth_id = :oAuthId AND o_auth_provider = :oAuthProvider", nativeQuery = true)
    Optional<UserEntity> findByOAuthIdContainingAndOAuthProviderContaining(String oAuthId, String oAuthProvider);
}
