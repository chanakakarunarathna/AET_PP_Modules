package com.placepass.utills.email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Email address validate using "org.apache.commons.validator.routines.EmailValidator" in annotation
 *
 */

public class EmailConstraintValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email email) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.equals(""))
            return true;
        return ValidateEmail.isValidEmail(email);
    }

}
