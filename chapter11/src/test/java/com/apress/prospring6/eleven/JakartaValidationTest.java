package com.apress.prospring6.eleven;

import com.apress.prospring6.eleven.domain.Singer;
import com.apress.prospring6.eleven.domain.SingerTwo;
import com.apress.prospring6.eleven.validator.JakartaValidationCfg;
import com.apress.prospring6.eleven.validator.SingerValidationService;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JakartaValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JakartaValidationTest.class);

    @Test
    void testSingerValidation() {
        try (var ctx = new AnnotationConfigApplicationContext(JakartaValidationCfg.class)) {
            var singerBeanValidationService = ctx.getBean(SingerValidationService.class);
            Singer singer = new Singer();
            singer.setFirstName("J");
            singer.setLastName("Mayer");
            singer.setGenre(null);
            singer.setGender(null);
            var violations = singerBeanValidationService.validateSinger(singer);
            assertEquals(2, violations.size());
            listViolations(violations);
        }
    }

    @Test
    void testCountrySingerValidation() {
        try (var ctx = new AnnotationConfigApplicationContext(JakartaValidationCfg.class)) {
            var singerBeanValidationService = ctx.getBean(SingerValidationService.class);
            Singer singer = new Singer();
            singer.setFirstName("John");
            singer.setLastName("Mayer");
            singer.setGenre(Singer.Genre.COUNTRY);
            singer.setGender(null);
            var violations = singerBeanValidationService.validateSinger(singer);
            assertEquals(1, violations.size());
            listViolations(violations);
        }
    }

    private static void listViolations(Set<ConstraintViolation<Singer>> violations) {
        violations.forEach(violation -> LOGGER.info("Validation error for property: {} with value: {} with error message: {}",
                violation.getPropertyPath(),
                violation.getInvalidValue(),
                violation.getMessage()));
    }
}
