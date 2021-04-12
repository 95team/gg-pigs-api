package com.gg_pigs.user.service;

import com.gg_pigs.user.dto.CreateDtoUser;
import com.gg_pigs.user.dto.RetrieveDtoUser;
import com.gg_pigs.user.dto.UpdateDtoUser;

import java.util.List;

public interface UserService {

    /** CREATE */
    /** User 단건 생성 */
    Long createUser(CreateDtoUser userDto);


    /** RETRIEVE */
    /** User 단건 조회 (using id) */
    RetrieveDtoUser retrieveUser(Long userId);

    /** User 단건 조회 (using email) */
    RetrieveDtoUser retrieveUserByEmail(String email);

    /** User 전체 조회 */
    List<RetrieveDtoUser> retrieveAllUsers();


    /** UPDATE */
    /** User 단건 수정 */
    Long updateUser(Long userId, UpdateDtoUser updateDtoUser);


    /** DELETE */
    /** User 단건 생성 */
    void deleteUser(Long userId);
}
