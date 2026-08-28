package com.apress.prospring6.fifteen.problem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested item(s) not found")
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2L;

    private Long objIdentifier;

    public <T> NotFoundException(Class<T> cls) {
        super("table for " + cls.getSimpleName() + " is empty");
    }

    public <T> NotFoundException(Class<T> cls, Long id) {
        super(cls.getSimpleName() + " with id: " + id + " does not exists");
    }

    public Long getObjIdentifier() {
        return objIdentifier;
    }
}
