public class GPSecurityException extends RuntimeException {
    public GPSecurityException() {
        super();
    }

    public GPSecurityException(String message) {
        super(message);
    }

    public GPSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public GPSecurityException(Throwable cause) {
        super(cause);
    }

    protected GPSecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
