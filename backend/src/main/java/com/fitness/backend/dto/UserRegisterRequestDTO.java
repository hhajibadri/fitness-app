package com.fitness.backend.dto;

import java.time.LocalDate;

import com.fitness.backend.enums.Gender;
import com.fitness.backend.validation.Adult;
import com.fitness.backend.validation.Password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserRegisterRequestDTO {

  private String name;

  @NotBlank(message = "Must enter email")
  @Email(message = "Email must have valid format")
  private String email;

  @NotBlank(message = "Must enter password")
  @Password
  private String password;

  @Adult
  private LocalDate dateOfBirth;

  private Gender gender;

  @Positive(message = "Height must be positive")
  private double height;

  @Positive(message = "Weight must be positive")
  private double weight;

  @Positive(message = "Bodyfat must be positive")
  private double bodyFatPercentage;
}
