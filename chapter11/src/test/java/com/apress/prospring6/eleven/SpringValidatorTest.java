package com.apress.prospring6.eleven;

import com.apress.prospring6.eleven.domain.Address;
import com.apress.prospring6.eleven.domain.Blogger;
import com.apress.prospring6.eleven.domain.BloggerWithAddress;
import com.apress.prospring6.eleven.formatter.FormattingServiceCfg;
import com.apress.prospring6.eleven.validator.AddressValidator;
import com.apress.prospring6.eleven.validator.BloggerWithAddressValidator;
import com.apress.prospring6.eleven.validator.SimpleBloggerValidator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpringValidatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringValidatorTest.class);

    @Test
    void testSimpleBloggerValidator() throws MalformedURLException {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class,
                FormattingServiceCfg.class, SimpleBloggerValidator.class)) {
            var blogger = new Blogger("", "Pedala", LocalDate.of(2000, 1, 1),
                    new URL("https://none.co.uk"));

            var bloggerValidator = ctx.getBean(SimpleBloggerValidator.class);
            var result = new BeanPropertyBindingResult(blogger, "blogger");

            ValidationUtils.invokeValidator(bloggerValidator, blogger, result);

            var errors = result.getAllErrors();
            assertEquals(1, errors.size());
            errors.forEach(e -> LOGGER.info("Object '{}' failed validation. Error code: {}", e.getObjectName(), e.getCode()));
        }
    }

    @Test
    void testBloggerWithAddressValidator() throws MalformedURLException {
        try (var ctx = new AnnotationConfigApplicationContext(AppConfig.class, FormattingServiceCfg.class,
                AddressValidator.class, BloggerWithAddressValidator.class)) {
            var address = new Address("221B", "UK");
            var blogger = new BloggerWithAddress(null, "Mazzie", LocalDate.of(1973, 1, 1),
                    null, address);

            var bloggerValidator = ctx.getBean(BloggerWithAddressValidator.class);
            var result = new BeanPropertyBindingResult(blogger, "blogger");

            ValidationUtils.invokeValidator(bloggerValidator, blogger, result);

            var errors = result.getAllErrors();
            assertEquals(2, errors.size());
            errors.forEach(e -> LOGGER.info("Error Code: {}", e.getCode()));

        }
    }
}
