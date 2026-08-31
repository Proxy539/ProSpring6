package com.apress.prospring6.twenty.boot.service;

import com.apress.prospring6.twenty.boot.model.Singer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SingerService {

    Flux<Singer> findAll();
    Mono<Singer> findById(Long id);
    Mono<Singer> findByFirstNameAndLastName(String firstName, String lastName);
    Flux<Singer> findByFirstName(String firstName);
    Mono<Singer> save(Singer singer);
    Mono<Singer> update(Long id, Singer actorMon);
    Mono<Void> delete(Long id);

    Flux<Singer> findByCriteriaDto(CriteriaDto criteria);

    @Getter
    @Setter
    @NoArgsConstructor
    class CriteriaDto {
        private String fieldName;
        private String fieldValue;
    }

    class CriteriaValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            return (CriteriaDto.class).isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            ValidationUtils.rejectIfEmpty(errors, "fieldName", "required", new Object[]{"fieldName"}, "Field Name is required!");
            ValidationUtils.rejectIfEmpty(errors, "fieldValue", "required", new Object[]{"fieldValue"}, "Field Value is required!");
        }
    }

    enum FieldGroup {
        FIRSTNAME,
        LASTNAME,
        BIRTHDATE;

        public static FieldGroup getField(String field) {
            return FieldGroup.valueOf(field.toUpperCase());
        }
    }
}
