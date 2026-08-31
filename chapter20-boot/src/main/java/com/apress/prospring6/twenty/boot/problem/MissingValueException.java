package com.apress.prospring6.twenty.boot.problem;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class MissingValueException extends RuntimeException {

    private Map<String, String> fieldNames;

    public MissingValueException(String message, Map<String, String> fieldNames) {
        super(message);
        this.fieldNames = fieldNames;
    }

    public MissingValueException(String message, Throwable cause, Map<String, String> fieldNames) {
        super(message, cause);
        this.fieldNames = fieldNames;
    }

    public Map<String, String> getFieldNames() {
        return fieldNames;
    }

    public static MissingValueException of(List<ObjectError> errors) {
        final List<String> fields = new ArrayList<>();
        var fieldNames = new HashMap<String, String>();
        errors.forEach(err -> fieldNames.put(((FieldError) err).getField(), err.getDefaultMessage()));
        return new MissingValueException("Some values are missing!", fieldNames);
    }
}
