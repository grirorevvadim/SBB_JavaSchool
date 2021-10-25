package com.tsystems.javaschool.projects.SBB.domain.validation;



import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotUniqueEmail  {
    String message() default "User with this email has already been registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
