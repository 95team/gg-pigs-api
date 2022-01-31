package com.gg_pigs._common;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.global.security.GPUserDetails;

public class GPUserDetailsGenerator {

    public static GPUserDetails getInstance() {
        return new GPUserDetails();
    }

    public static GPUserDetails getInstance(User user) {
        return new GPUserDetails(user);
    }
}
