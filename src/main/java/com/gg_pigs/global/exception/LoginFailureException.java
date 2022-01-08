package com.gg_pigs.global.exception;

public class LoginFailureException extends RuntimeException {

    public LoginFailureException() {
        super();
    }

    public LoginFailureException(String s) {
        super(s);
    }
}
