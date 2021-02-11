package com.gg_pigs.user.dto;

import com.gg_pigs.user.entity.User;
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
    private String oauth_type;
    private Character isActivated;
    private Character isAuthenticated;

    public static RetrieveDtoUser createRetrieveDtoUser(User user) {
        return RetrieveDtoUser.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .oauth_type(user.getOauthType())
                .isActivated(user.getIsActivated())
                .isAuthenticated(user.getIsAuthenticated())
                .build();
    }
}
