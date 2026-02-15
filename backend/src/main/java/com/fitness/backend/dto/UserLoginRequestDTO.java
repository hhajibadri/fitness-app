package com.fitness.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequestDTO {

  @NotBlank(message = "Must enter email")
  @Email(message = "Email must have valid format")
  private String email;

  @NotBlank(message = "Must enter password")
  private String password;

}
