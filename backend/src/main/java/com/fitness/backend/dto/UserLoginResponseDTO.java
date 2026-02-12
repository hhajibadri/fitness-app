package com.fitness.backend.dto;

import com.fitness.backend.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResponseDTO {
  private Long userId;
  private Role role;
}
