package com.fitness.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.backend.dto.UserLoginRequestDTO;
import com.fitness.backend.dto.UserLoginResultDTO;
import com.fitness.backend.dto.UserRegisterRequestDTO;
import com.fitness.backend.enums.Role;
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


  public void registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {

    if (userRepository.existsByEmail(userRegisterRequestDTO.getEmail())) {
      throw new IllegalArgumentException("Email already in use");
    }
    String hashedPassword = passwordEncoder.encode(userRegisterRequestDTO.getPassword());
    User user = User.builder()
                  .name(userRegisterRequestDTO.getName())
                  .email(userRegisterRequestDTO.getEmail())
                  .password(hashedPassword)
                  .dateOfBirth(userRegisterRequestDTO.getDateOfBirth())
                  .gender(userRegisterRequestDTO.getGender())
                  .height(userRegisterRequestDTO.getHeight())
                  .weight(userRegisterRequestDTO.getWeight())
                  .bodyFatPercentage(userRegisterRequestDTO.getBodyFatPercentage())
                  .build();
    userRepository.save(user);
  }

  public UserLoginResultDTO loginUser(UserLoginRequestDTO userLoginRequestDTO) {

    User potentialUser = userRepository.findByEmail(userLoginRequestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!passwordEncoder.matches(userLoginRequestDTO.getPassword(), potentialUser.getPassword())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    String token = jwtUtil.generateToken(potentialUser);

    return new UserLoginResultDTO(
      potentialUser.getId(),
      potentialUser.getRole(),
      token
    );

  }

  public void deleteUser(Long targetUserId, String requesterEmail) {

    User requester = userRepository.findByEmail(requesterEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
    User target = userRepository.findById(targetUserId).orElseThrow(() -> new IllegalArgumentException("User not found"));

    boolean isAdmin = requester.getRole() == Role.ADMIN;
    boolean isSelf = requester.getId().equals(target.getId());

    if (!isAdmin && !isSelf) {
      throw new IllegalArgumentException("Not authorized to delete user");
    }

    userRepository.delete(target);

  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

}
