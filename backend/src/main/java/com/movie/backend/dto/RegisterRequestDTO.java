package com.movie.backend.dto;

import java.time.LocalDate;

import com.movie.backend.validation.Adult;
import com.movie.backend.validation.Password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequestDTO {
  
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password is required")
  @Password
  private String password;

  @Adult
  private LocalDate dateOfBirth;

  private String name;
}
