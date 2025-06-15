package com.shinom.blogging.helper;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VerifiedValidator implements ConstraintValidator<MustBeVerified, Boolean> {

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        // Ensure verified is true
        return value != null && value;
    }
}

