package com.gg_pigs.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDtoUser {

    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String oauth_type;
    private String isActivated;
    private String isAuthenticated;
}
