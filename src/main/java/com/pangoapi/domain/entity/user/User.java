package com.pangoapi.domain.entity.user;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class User {
    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String role;
    private String oauthType;
    private Boolean isActivated;
    private Boolean isAuthenticated;
}