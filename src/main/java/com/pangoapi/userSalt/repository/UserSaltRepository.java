package com.pangoapi.userSalt.repository;

import com.pangoapi.userSalt.entity.UserSalt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSaltRepository extends JpaRepository<UserSalt, Long>  {
}
