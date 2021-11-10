package com.tsystems.javaschool.projects.SBB.domain.validation;

import com.tsystems.javaschool.projects.SBB.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements
        ConstraintValidator<NotUniqueEmail, String> {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public void initialize(NotUniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userServiceImpl.findUserByEmail(email) == null;
        //return true;
    }
}
