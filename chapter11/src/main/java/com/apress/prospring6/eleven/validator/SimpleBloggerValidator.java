package com.apress.prospring6.eleven.validator;

import com.apress.prospring6.eleven.domain.Blogger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("simpleBloggerValidator")
public class SimpleBloggerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Blogger.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "field.required");
    }
}
