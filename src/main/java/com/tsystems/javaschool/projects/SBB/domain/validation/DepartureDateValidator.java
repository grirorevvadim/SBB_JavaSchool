package com.tsystems.javaschool.projects.SBB.domain.validation;

import com.tsystems.javaschool.projects.SBB.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartureDateValidator implements
        ConstraintValidator<NotPastDate, String> {
    @Autowired
    TrainService trainService;

    @Override
    public void initialize(NotPastDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        return trainService.checkValidDepartureDate(date);
    }
}
