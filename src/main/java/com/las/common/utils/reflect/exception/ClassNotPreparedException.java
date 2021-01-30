package com.las.common.utils.reflect.exception;

public class ClassNotPreparedException extends RuntimeException {
    public ClassNotPreparedException() {
    }

    public ClassNotPreparedException(String message) {
        super(message);
    }

    public ClassNotPreparedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassNotPreparedException(Throwable cause) {
        super(cause);
    }
}
