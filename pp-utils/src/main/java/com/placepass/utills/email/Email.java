package com.placepass.utills.email;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Email address validate using "org.apache.commons.validator.routines.EmailValidator" in annotation
 *
 */

@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "email is not well formed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}