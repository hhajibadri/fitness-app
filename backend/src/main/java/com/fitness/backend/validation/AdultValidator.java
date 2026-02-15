package com.fitness.backend.validation;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

  @Override
  public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
    if (dateOfBirth == null)
      return false;

    return dateOfBirth.getYear() >= 18;
  }

}
