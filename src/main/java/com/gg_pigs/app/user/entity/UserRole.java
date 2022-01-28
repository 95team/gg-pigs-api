package com.gg_pigs.app.user.entity;

public enum UserRole {
    ROLE_ADMIN, ROLE_USER;

    public String suffixName() {
        return this.name().split("_")[1];
    }
}
