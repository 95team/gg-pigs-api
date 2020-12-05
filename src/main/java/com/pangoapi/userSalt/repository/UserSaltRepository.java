package com.pangoapi.userSalt.repository;

import com.pangoapi.user.entity.User;
import com.pangoapi.userSalt.entity.UserSalt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSaltRepository extends JpaRepository<UserSalt, Long>  {

    Optional<UserSalt> findUserSaltByUserId(Long userId);
}
