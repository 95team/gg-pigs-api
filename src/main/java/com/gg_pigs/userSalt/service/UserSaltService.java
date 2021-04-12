package com.gg_pigs.userSalt.service;

import com.gg_pigs.user.entity.User;
import com.gg_pigs.userSalt.dto.RetrieveDtoUserSalt;

public interface UserSaltService {

    /** CREATE */
    /** UserSalt 단건 생성 */
    Long createUserSalt(User user, String password);

    /** RETRIEVE */
    /** UserSalt 단건 조회 */
    RetrieveDtoUserSalt retrieveUserSalt(Long userId);
}
