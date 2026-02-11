package com.fitness.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.backend.dto.AuthResponseDTO;
import com.fitness.backend.model.User;
import com.fitness.backend.repository.UserRepository;
import com.fitness.backend.security.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;


  public User registerUser(User user) {

    if (userRepository.existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email already in use");
    }

    String hashedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepository.save(user);
  }

  public String loginUser(User user) {

    User potentialUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    if (!passwordEncoder.matches(user.getPassword(), potentialUser.getPassword())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public boolean checkPassword(User user, String rawPassword) {
    return passwordEncoder.matches(rawPassword, user.getPassword());
  }

}
