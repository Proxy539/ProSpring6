package com.apress.prospring6.eleven.validator;

import com.apress.prospring6.eleven.domain.Singer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("singerValidationService")
public class SingerValidationService {

    private final Validator validator;

    public SingerValidationService(Validator validator) {
        this.validator = validator;
    }

    public Set<ConstraintViolation<Singer>> validateSinger(Singer singer) {
        return validator.validate(singer);
    }
}
