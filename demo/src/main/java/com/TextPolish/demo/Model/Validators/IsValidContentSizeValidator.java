package com.TextPolish.demo.Model.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsValidContentSizeValidator implements ConstraintValidator<IsValidContentSize,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty())
            return false;
        String[] words = value.split("\\s+");
        return words.length <= 30;
    }
}
