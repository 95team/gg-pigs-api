package com.gg_pigs.userSalt.service;

import com.gg_pigs.user.entity.User;

public interface UserSaltService {

    /**
     * CREATE: UserSalt 단건 생성
     */
    Long createUserSalt(User user, String password);
}
