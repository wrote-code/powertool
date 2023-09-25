package com.wrotecode.powertool.exception;

public class CoreRuntimeException extends RuntimeException{
    public CoreRuntimeException() {
        super();
    }

    public CoreRuntimeException(String message) {
        super(message);
    }

    public CoreRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreRuntimeException(Throwable cause) {
        super(cause);
    }

    protected CoreRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
