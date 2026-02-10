package com.fitness.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
  
  @NotBlank(message = "Must enter email")
  @Email(message = "Email must have valid format")
  private String email;

  @NotBlank(message = "Must enter password")
  private String password;

}
