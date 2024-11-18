package com.team4.lp_back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.team4.lp_back.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserName(String userName);

    Boolean existsByUserName(String userName);
}
