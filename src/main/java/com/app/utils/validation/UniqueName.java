package com.app.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueNameValidator.class)
public @interface UniqueName {
    String message() default "Company already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}




