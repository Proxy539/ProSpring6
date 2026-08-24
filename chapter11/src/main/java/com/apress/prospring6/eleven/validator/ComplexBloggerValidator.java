package com.apress.prospring6.eleven.validator;

import com.apress.prospring6.eleven.domain.Blogger;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component("complexBloggerValidator")
public class ComplexBloggerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Blogger.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var b = (Blogger) target;
        if (StringUtils.isEmpty(b.firstName()) && StringUtils.isEmpty(b.lastName())) {
            errors.rejectValue("firstName", "firstNameOrLastName.required");
            errors.rejectValue("lastName", "firstNameOrLastName.required");
        }

        if (b.birthDate().isBefore(LocalDate.of(1983, 1, 1))) {
            errors.rejectValue("birthDate", "birthDate.greaterThan1983");
        }
    }
}
