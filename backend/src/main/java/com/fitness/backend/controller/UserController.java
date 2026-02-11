package com.fitness.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.backend.dto.UserLoginDTO;
import com.fitness.backend.dto.UserRegisterDTO;
import com.fitness.backend.model.User;
import com.fitness.backend.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  public ResponseEntity<String> handleServerError(Exception ex) {
    return ResponseEntity.
            status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Unexpected server error");
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
    User user = new User();
    user.setName(userRegisterDTO.getName());
    user.setEmail(userRegisterDTO.getEmail());
    user.setDateOfBirth(userRegisterDTO.getDateOfBirth());
    user.setGender(userRegisterDTO.getGender());
    user.setHeight(userRegisterDTO.getHeight());
    user.setWeight(userRegisterDTO.getWeight());
    user.setBodyFatPercentage(userRegisterDTO.getBodyFatPercentage());
    userService.registerUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {

    return ResponseEntity.status(HttpStatus.OK).build();
  }
  
  
}
