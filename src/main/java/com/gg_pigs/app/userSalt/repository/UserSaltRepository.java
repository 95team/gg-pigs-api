package com.gg_pigs.app.userSalt.repository;

import com.gg_pigs.app.userSalt.entity.UserSalt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSaltRepository extends JpaRepository<UserSalt, Long>  {

    Optional<UserSalt> findUserSaltByUserId(Long userId);
}
