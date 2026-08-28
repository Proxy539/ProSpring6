package com.apress.prospring6.sixteen.boot.problem;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public <T> NotFoundException(Class<T> cls) {
        super("table for " + cls.getSimpleName() + " is empty");
    }

    public <T> NotFoundException(Class<T> cls, Long id) {
        super(cls.getSimpleName() + " with id: " + id + " does not exists");
    }
}
