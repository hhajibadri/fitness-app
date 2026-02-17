package com.fitness.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

  }

}
