package com.gg_pigs.modules.security;

class GPUnauthenticatedException extends GPSecurityException {
    public GPUnauthenticatedException() {
        super();
    }

    public GPUnauthenticatedException(String message) {
        super(message);
    }

    public GPUnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GPUnauthenticatedException(Throwable cause) {
        super(cause);
    }

    protected GPUnauthenticatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
