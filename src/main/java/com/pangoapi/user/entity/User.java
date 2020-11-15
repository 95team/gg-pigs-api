package com.pangoapi.user.entity;

import com.pangoapi.dto.user.CreateDtoUser;
import com.pangoapi.dto.user.UpdateDtoUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String role;
    private String oauthType;
    private Character isActivated;
    private Character isAuthenticated;

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeRole(String role) {
        this.role = role;
    }

    public void changeOauthType(String oauthType) {
        this.oauthType = oauthType;
    }

    public void changeActivated(Character activated) {
        isActivated = activated;
    }

    public void changeAuthenticated(Character authenticated) {
        isAuthenticated = authenticated;
    }

    public void changeUser(UpdateDtoUser updateDtoUser) {
        if(updateDtoUser.getName() != null)     changeName(updateDtoUser.getName());
        if(updateDtoUser.getEmail() != null)    changeEmail(updateDtoUser.getEmail());
        if(updateDtoUser.getPhone() != null)    changePhone(updateDtoUser.getPhone());
    }

    public static User createUser(CreateDtoUser createDtoUser) {
        if(createDtoUser.getEmail() == null) {
            throw new IllegalArgumentException("이메일은 필수값 입니다. (Please check the data 'email')");
        }

        String defaultName = null;
        String defaultEmail = createDtoUser.getEmail();
        String defaultPhone = null;
        String defaultRole = "ROLE_USER";
        String defaultOauthType = null;
        Character defaultIsActivated = 'N';
        Character defaultIsAuthenticated = 'N';

        return User.builder()
                .id(null)
                .name(defaultName)
                .email(defaultEmail)
                .phone(defaultPhone)
                .role(defaultRole)
                .oauthType(defaultOauthType)
                .isActivated(defaultIsActivated)
                .isAuthenticated(defaultIsAuthenticated)
                .build();
    }
}