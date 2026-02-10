package com.fitness.backend.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
  
  String message() default "Password must be at least 8 characters and contain at least one number, one lowercase letter, and one uppercase letter";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
