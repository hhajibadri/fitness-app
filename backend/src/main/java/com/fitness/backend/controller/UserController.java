package com.fitness.backend.controller;

import java.time.Duration;

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

import com.fitness.backend.dto.RefreshRequest;
import com.fitness.backend.dto.RefreshResponse;
import com.fitness.backend.dto.UserLoginRequest;
import com.fitness.backend.dto.UserLoginResponse;
import com.fitness.backend.dto.UserRegisterRequest;
import com.fitness.backend.model.RefreshToken;
import com.fitness.backend.security.JwtUtil;
import com.fitness.backend.service.RefreshTokenService;
import com.fitness.backend.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final RefreshTokenService refreshTokenService;
  private final JwtUtil jwtUtil;
  private final Duration accessTokenDuration = Duration.ofMinutes(15);
  private final Duration refreshTokenDuration = Duration.ofDays(14);

  public UserController(UserService userService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.refreshTokenService = refreshTokenService;
    this.jwtUtil = jwtUtil;
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleServerError(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Unexpected server error");
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
    userService.registerUser(userRegisterRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest,
      HttpServletResponse httpResponse) {
    UserLoginResponse userLoginResultDTO = userService.loginUser(userLoginRequest);
    return ResponseEntity.ok(userLoginResultDTO);
  }

  @PostMapping("/refresh")
  public ResponseEntity<RefreshResponse> refreshToken(@Valid @RequestBody RefreshRequest refreshRequest,
      HttpServletResponse httpResponse) {

    RefreshToken oldToken = refreshTokenService.getByToken(refreshRequest.token());
    refreshTokenService.verifyRefreshToken(oldToken);

    RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(oldToken);

    String newAccessToken = jwtUtil.generateToken(newRefreshToken.getUser());

    RefreshResponse refreshResponse = new RefreshResponse(
        newAccessToken,
        newRefreshToken.getToken(),
        accessTokenDuration.getSeconds(),
        refreshTokenDuration.getSeconds());

    return ResponseEntity.ok(refreshResponse);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logoutUser(Authentication authentication, HttpServletResponse httpResponse) {

    if (authentication != null) {
      refreshTokenService.deleteByUser(userService.getUserByEmail(authentication.getName()));
    }

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id, Authentication authentication) {

    if (authentication != null) {
      String requesterEmail = authentication.getName();
      userService.deleteUser(id, requesterEmail);
    }

    return ResponseEntity.noContent().build();
  }

}
