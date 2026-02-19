package com.fitness.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.backend.dto.UpdateUserProfileRequest;
import com.fitness.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/profile")
public class UserProfileController {

  private final UserService userService;

  public UserProfileController(UserService userService) {
    this.userService = userService;
  }

  @PatchMapping
  public ResponseEntity<Void> updateUserProfile(@Valid @RequestBody UpdateUserProfileRequest details, Authentication authentication) {
    String userEmail = authentication.getName();
    userService.updateUserProfile(userEmail, details);
    return ResponseEntity.noContent().build();
  }

}
