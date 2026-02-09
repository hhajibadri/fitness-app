package com.movie.backend.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
  
  String message() default "Password must be at least 8 characters and contain at least one number, one lowercase letter, and one uppercase letter"
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}
