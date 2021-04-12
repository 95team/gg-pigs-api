package com.gg_pigs.userSalt.dto;

import com.gg_pigs.userSalt.entity.UserSalt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RetrieveDtoUserSalt {

    private Long userSaltId;
    private String salt;
    private String digest;

    public static RetrieveDtoUserSalt createRetrieveDtoUserSalt(UserSalt userSalt) {
        return RetrieveDtoUserSalt.builder()
                .userSaltId(userSalt.getId())
                .salt(userSalt.getSalt())
                .digest(userSalt.getDigest())
                .build();
    }
}
