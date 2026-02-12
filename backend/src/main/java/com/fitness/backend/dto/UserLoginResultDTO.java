package com.fitness.backend.dto;

import com.fitness.backend.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginResultDTO {
  private Long userId;
  private Role role;
  private String token;
}
