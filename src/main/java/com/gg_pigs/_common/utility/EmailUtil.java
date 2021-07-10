package com.gg_pigs._common.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    /** Mail */
    public static final Pattern ALLOWABLE_EMAIL_FORMAT_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static Boolean checkEmailFormat(String email) {
        boolean result = false;

        Pattern pattern = ALLOWABLE_EMAIL_FORMAT_PATTERN;
        Matcher matcher = pattern.matcher(email);

        if(matcher.find()) { result = true; }

        return result;
    }
}
