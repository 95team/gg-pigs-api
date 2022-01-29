package com.gg_pigs.modules.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class GPAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        GPAuthenticationToken gpAuthenticationToken = (GPAuthenticationToken) authentication;
        if(gpAuthenticationToken.getPrincipal() == null) {
            return null;
        }

        return gpAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(GPAuthenticationToken.class);
    }
}
