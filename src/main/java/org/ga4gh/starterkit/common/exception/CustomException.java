package org.ga4gh.starterkit.common.exception;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
