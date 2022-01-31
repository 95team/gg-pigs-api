package com.gg_pigs._common;

import com.gg_pigs.security.GPAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class GPAuthenticationTokenGenerator {

    public static GPAuthenticationToken getInstance() {
        return GPAuthenticationToken.authenticatedEmptyToken();
    }

    public static GPAuthenticationToken getInstance(UserDetails userDetails) {
        return new GPAuthenticationToken(userDetails, userDetails.getAuthorities());
    }
}
