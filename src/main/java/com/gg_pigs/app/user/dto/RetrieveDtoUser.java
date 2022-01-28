package com.gg_pigs.app.user.dto;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RetrieveDtoUser {

    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String oauthType;
    private Character isActivated;
    private Character isAuthenticated;

    public User toEntity() {
        return User.builder()
                .id(userId)
                .name(name)
                .email(email)
                .phone(phone)
                .role(UserRole.valueOf(role))
                .oauthType(oauthType)
                .isActivated(isActivated)
                .isAuthenticated(isAuthenticated)
                .build();
    }

    public static RetrieveDtoUser of(User user) {
        return RetrieveDtoUser.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .oauthType(user.getOauthType())
                .isActivated(user.getIsActivated())
                .isAuthenticated(user.getIsAuthenticated())
                .build();
    }
}
