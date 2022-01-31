package com.gg_pigs.global.exception;

public class GPException extends RuntimeException {
    public GPException() {
        super();
    }

    public GPException(String message) {
        super(message);
    }

    public GPException(String message, Throwable cause) {
        super(message, cause);
    }

    public GPException(Throwable cause) {
        super(cause);
    }

    protected GPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
