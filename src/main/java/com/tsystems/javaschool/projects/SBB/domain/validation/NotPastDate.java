package com.tsystems.javaschool.projects.SBB.domain.validation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DepartureDateValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotPastDate {
    String message() default "Selected departure date is in the past";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
