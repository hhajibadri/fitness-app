package com.fitness.backend.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdultValidator implements ConstraintValidator<Adult, String> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

  @Override
  public boolean isValid(String dateOfBirth, ConstraintValidatorContext context) {
    
    System.out.println(dateOfBirth);

    if (dateOfBirth == null || dateOfBirth.isBlank()) {
      return false;
    }

    try {
        LocalDate dob = LocalDate.parse(dateOfBirth, FORMATTER);
        return Period.between(dob, LocalDate.now()).getYears() >= 18;
    } catch (DateTimeParseException e) {
      return false;
    }

  }

}
