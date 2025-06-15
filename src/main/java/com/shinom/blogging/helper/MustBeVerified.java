package com.shinom.blogging.helper;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VerifiedValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MustBeVerified {
    String message() default "Email must be verified";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

