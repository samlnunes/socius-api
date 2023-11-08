package com.api.socius.repositories;

import com.api.socius.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);

    UserDetails findByUsername(String username);
}
