package com.gg_pigs.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RequestDtoLogin {

    String email;
    String password;
    String digest;
    String role;
}
