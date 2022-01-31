package com.gg_pigs.global.security;

import com.gg_pigs.app.user.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ToString
@Getter
public class GPUserDetails implements com.gg_pigs.security.GPUserDetails {

    private final User user;
    private final List<SimpleGrantedAuthority> authorities;

    public GPUserDetails() {
        this.user = new User();
        this.authorities = new ArrayList<>();
    }

    public GPUserDetails(User user) {
        this.user = user;

        this.authorities = new ArrayList<>();
        this.authorities.add(new SimpleGrantedAuthority(this.user.getRole().name()));
    }

    public static GPUserDetails of(User user) {
        return new GPUserDetails(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return (this.user.getIsActivated() == 'Y' && this.user.getIsAuthenticated() == 'Y');
    }
}
