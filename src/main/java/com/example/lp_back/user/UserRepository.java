package com.example.lp_back.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(String id);

    Boolean existsById(String id);
}