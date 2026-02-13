package com.fitness.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.backend.dto.UserLoginRequestDTO;
import com.fitness.backend.dto.UserLoginResponseDTO;
import com.fitness.backend.dto.UserLoginResultDTO;
import com.fitness.backend.dto.UserRegisterRequestDTO;
import com.fitness.backend.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleServerError(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Unexpected server error");
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.getMessage());
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
    userService.registerUser(userRegisterRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<UserLoginResponseDTO> loginUser(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse httpResponse) {
    UserLoginResultDTO userLoginResultDTO = userService.loginUser(userLoginRequestDTO);
    Cookie cookie = new Cookie("access_token", userLoginResultDTO.getToken());
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60); // 1 hour
    httpResponse.addCookie(cookie);
    return ResponseEntity.ok(new UserLoginResponseDTO(userLoginResultDTO.getUserId(), userLoginResultDTO.getRole()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id, Authentication authentication) {
      String requesterEmail = authentication.getName();
      userService.deleteUser(id, requesterEmail);
      return ResponseEntity.noContent().build();
  }
  
  
  
}
