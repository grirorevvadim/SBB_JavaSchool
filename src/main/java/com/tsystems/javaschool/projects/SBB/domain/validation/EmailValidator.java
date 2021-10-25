package com.tsystems.javaschool.projects.SBB.domain.validation;

import com.tsystems.javaschool.projects.SBB.domain.dto.UserDTO;
import com.tsystems.javaschool.projects.SBB.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements
        ConstraintValidator<NotUniqueEmail, UserDTO> {
    @Override
    public void initialize(NotUniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext constraintValidatorContext) {

        //return userService.findUserByEmail(user.getEmail()) != null;
        return true;
    }
}
