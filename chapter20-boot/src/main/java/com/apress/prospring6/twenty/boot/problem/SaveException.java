package com.apress.prospring6.twenty.boot.problem;

import java.io.Serial;

public class SaveException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public SaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
