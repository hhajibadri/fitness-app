package com.fitness.backend.service;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.backend.dto.UserLoginRequest;
import com.fitness.backend.dto.UserLoginResponse;
import com.fitness.backend.dto.UserProfileDetail;
import com.fitness.backend.dto.UserRegisterRequest;
import com.fitness.backend.enums.Role;
import com.fitness.backend.exception.EmailAlreadyInUseException;
import com.fitness.backend.exception.InvalidCredentialsException;
import com.fitness.backend.exception.UnauthorizedActionException;
import com.fitness.backend.exception.UserNotFoundException;
import com.fitness.backend.model.RefreshToken;
import com.fitness.backend.model.User;
import com.fitness.backend.model.UserProfile;
import com.fitness.backend.repository.UserRepository;
import com.fitness.backend.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final RefreshTokenService refreshTokenService;
  private final UserRepository userRepository;

  public UserService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
    this.refreshTokenService = refreshTokenService;
    this.userRepository = userRepository;
  }

  @Transactional
  public void registerUser(UserRegisterRequest userRegisterRequest) {
    if (userRepository.existsByEmail(userRegisterRequest.email())) {
      throw new EmailAlreadyInUseException();
    }
    String hashedPassword = passwordEncoder.encode(userRegisterRequest.password());
    User user = new User();
    user.setName(userRegisterRequest.name());
    user.setEmail(userRegisterRequest.email());
    user.setPassword(hashedPassword);
    userRepository.save(user);
  }

  @Transactional
  public UserLoginResponse loginUser(UserLoginRequest userLoginRequestDTO) {

    User user = userRepository.findByEmail(userLoginRequestDTO.email())
        .orElseThrow(() -> new InvalidCredentialsException());

    if (!passwordEncoder.matches(userLoginRequestDTO.password(), user.getPassword())) {
      throw new InvalidCredentialsException();
    }

    UserProfile profile = user.getUserProfile();

    String accessToken = jwtUtil.generateToken(user);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

    Instant accessExpiry = jwtUtil.getExpiry(accessToken);
    Instant refreshExpiry = refreshToken.getExpiryInstant();

    long accessTokenTTL = Duration.between(Instant.now(), accessExpiry).getSeconds();
    long refreshTokenTTL = Duration.between(Instant.now(), refreshExpiry).getSeconds();

    String issuedAtIso = jwtUtil.getIssuedAt(accessToken).toString();

    return new UserLoginResponse(
        user.getId(),
        user.getRole().name(),
        accessToken,
        accessTokenTTL,
        refreshToken.getToken(),
        refreshTokenTTL,
        issuedAtIso,
        new UserProfileDetail(
            user.getName(),
            user.getEmail(),
            profile.getDateOfBirth().format(DateTimeFormatter.ISO_DATE), // ISO date for mobile
            profile.getGender().name(),
            profile.getHeight(),
            profile.getWeight(),
            profile.getBodyFatPercentage()));
  }

  @Transactional
  public void logoutUser(String email) {
    Optional<User> maybeUser = userRepository.findByEmail(email);
    maybeUser.ifPresent(user -> refreshTokenService.deleteByUser(user));
  }

  @Transactional
  public void deleteUser(Long targetUserId, String requesterEmail) {
    User requester = userRepository.findByEmail(requesterEmail)
        .orElseThrow(() -> new UserNotFoundException());
    User target = userRepository.findById(targetUserId)
        .orElseThrow(() -> new UserNotFoundException());
    boolean isAdmin = requester.getRole().equals(Role.ADMIN);
    boolean isSelf = requester.getId().equals(target.getId());

    if (!isAdmin && !isSelf) {
      throw new UnauthorizedActionException();
    }

    userRepository.delete(target);

  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
  }

}
