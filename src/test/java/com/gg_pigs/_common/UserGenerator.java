package com.gg_pigs._common;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.entity.UserRole;

public class UserGenerator {
    public static User getInstance(Long id) {
        return getInstance(id, UserRole.ROLE_USER, 'Y', 'Y');
    }

    public static User getInstance(Long id, UserRole role) {
        return getInstance(id, role, 'Y', 'Y');
    }

    public static User getInstance(Long id, UserRole role, Character isActivated, Character isAuthenticated) {
        return User.builder()
                   .id(id)
                   .name("name")
                   .email("pigs95team@gmail.com")
                   .phone("010-1234-5678")
                   .role(role)
                   .oauthType("null")
                   .isActivated(isActivated)
                   .isAuthenticated(isAuthenticated)
                   .build();
    }
}
