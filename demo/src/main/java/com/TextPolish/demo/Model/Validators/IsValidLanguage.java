package com.TextPolish.demo.Model.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsValidLanguageValidator.class)
public @interface IsValidLanguage{
    String message() default "Invalid language";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
