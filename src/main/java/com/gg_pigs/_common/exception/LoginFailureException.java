package com.gg_pigs._common.exception;

public class LoginFailureException extends RuntimeException {

    public LoginFailureException() {
        super();
    }

    public LoginFailureException(String s) {
        super(s);
    }
}
