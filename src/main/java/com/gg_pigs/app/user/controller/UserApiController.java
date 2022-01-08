package com.gg_pigs.app.user.controller;

import com.gg_pigs.global.dto.ApiResponse;
import com.gg_pigs.app.user.dto.CreateDtoUser;
import com.gg_pigs.app.user.dto.RetrieveDtoUser;
import com.gg_pigs.app.user.dto.UpdateDtoUser;
import com.gg_pigs.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    /** CREATE */
    @PostMapping("/api/v1/users")
    public ApiResponse create(@RequestBody CreateDtoUser createDtoUser) {
        Long userId = userService.create(createDtoUser);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userId);
    }

    /** READ */
    @GetMapping("/api/v1/users/{userId}")
    public ApiResponse read(@PathVariable("userId") Long _userId) {
        RetrieveDtoUser retrieveDtoUser = userService.read(_userId);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoUser);
    }

    @GetMapping("/api/v1/users")
    public ApiResponse readAll() {
        List<RetrieveDtoUser> allRetrieveDtoUsers = userService.retrieveAllUsers();

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), allRetrieveDtoUsers);
    }

    /** UPDATE */
    @PutMapping("/api/v1/users/{userId}")
    public ApiResponse update(@PathVariable("userId") Long _userId, @RequestBody UpdateDtoUser updateDtoUser) {
        Long userId = userService.update(_userId, updateDtoUser);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), userId);
    }

    /** DELETE */
    @DeleteMapping("/api/v1/users/{userId}")
    public ApiResponse delete(@PathVariable("userId") Long _userId) {
        userService.delete(_userId);

        return new ApiResponse(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), new ArrayList<>());
    }
}
