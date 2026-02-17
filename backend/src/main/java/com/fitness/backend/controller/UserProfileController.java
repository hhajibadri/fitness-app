package com.fitness.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.backend.service.UserService;

@RestController
@RequestMapping("/api/users/profile")
public class UserProfileController {

  private final UserService userService;

  public UserProfileController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<Void> postMethodName(@RequestBody String entity) {
    // TODO: process POST request

    return ResponseEntity.noContent().build();
  }

}
