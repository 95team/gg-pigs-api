package com.gg_pigs.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GPAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public GPAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    public GPAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);

        if(principal == null) {
            setAuthenticated(false);
        }
        if(authorities.size() <= 0) {
            setAuthenticated(false);
        }
    }

    public static GPAuthenticationToken authenticatedEmptyToken() {
        GPAuthenticationToken gpAuthenticationToken = new GPAuthenticationToken(null);
        gpAuthenticationToken.setAuthenticated(true);

        return gpAuthenticationToken;
    }

    public static GPAuthenticationToken unauthenticatedEmptyToken() {
        return new GPAuthenticationToken(null);
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
