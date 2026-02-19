package com.fitness.backend.service;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitness.backend.dto.BasicUserDetail;
import com.fitness.backend.dto.UpdateEmailRequest;
import com.fitness.backend.dto.UpdateUserProfileRequest;
import com.fitness.backend.dto.UserDetail;
import com.fitness.backend.dto.UserLoginRequest;
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

  public UserService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService,
      UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.passwordEncoder = passwordEncoder;
    this.refreshTokenService = refreshTokenService;
    this.userRepository = userRepository;
  }

  @Transactional
  public void updateEmail(String currentEmail, UpdateEmailRequest request) {

    User user = getUserByEmail(currentEmail);

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new UnauthorizedActionException();
    }

    if (user.getEmail().equals(request.newEmail())) {
      return;
    }

    if (userRepository.existsByEmail(request.newEmail())) {
      throw new EmailAlreadyInUseException();
    }

    user.setEmail(request.newEmail());
    refreshTokenService.deleteByUser(user);
  }

  public BasicUserDetail getUserDetail(String email) {

    User user = getUserByEmail(email);

    UserProfile profile = user.getUserProfile();

    return new BasicUserDetail(
        user.getId(),
        user.getRole().name(),
        new UserProfileDetail(
            user.getName(),
            user.getEmail(),
            profile.getDateOfBirth().format(DateTimeFormatter.ISO_DATE),
            profile.getGender().name(),
            profile.getHeight(),
            profile.getWeight(),
            profile.getBodyFatPercentage()));

  }

  @Transactional
  public void registerUser(UserRegisterRequest userRegisterRequest) {
    if (userRepository.existsByEmail(userRegisterRequest.email())) {
      throw new EmailAlreadyInUseException();
    }
    String hashedPassword = passwordEncoder.encode(userRegisterRequest.password());
    User user = new User();
    UserProfile userProfile = new UserProfile();

    user.setName(userRegisterRequest.name());
    user.setEmail(userRegisterRequest.email());
    user.setPassword(hashedPassword);
    user.setUserProfile(userProfile);

    userProfile.setUser(user);
    userProfile.setDateOfBirth(userRegisterRequest.dateOfBirth());
    userProfile.setGender(userRegisterRequest.gender());
    userProfile.setHeight(userRegisterRequest.height());
    userProfile.setWeight(userRegisterRequest.weight());
    userProfile.setBodyFatPercentage(userRegisterRequest.bodyFatPercentage());

    userRepository.save(user);
  }

  @Transactional
  public UserDetail loginUser(UserLoginRequest userLoginRequest) {

    User user = getUserByEmail(userLoginRequest.email());

    if (!passwordEncoder.matches(userLoginRequest.password(), user.getPassword())) {
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

    return new UserDetail(
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
    User requester = getUserByEmail(requesterEmail);
    User target = getUserById(targetUserId);
    boolean isAdmin = requester.getRole() == Role.ADMIN;
    boolean isSelf = requester.getId().equals(target.getId());

    if (!isAdmin && !isSelf) {
      throw new UnauthorizedActionException();
    }

    userRepository.delete(target);

  }

  @Transactional
  public void updateUserProfile(String email, UpdateUserProfileRequest details) {

    User user = getUserByEmail(email);
    UserProfile userProfile = user.getUserProfile();

    if (details.dateOfBirth() != null) {
      userProfile.setDateOfBirth(details.dateOfBirth());
    }

    if (details.gender() != null) {
      userProfile.setGender(details.gender());
    }

    if (details.height() != null) {
      userProfile.setHeight(details.height());
    }

    if (details.weight() != null) {
      userProfile.setWeight(details.weight());
    }

    if (details.bodyFatPercentage() != null) {
      userProfile.setBodyFatPercentage(details.bodyFatPercentage());
    }
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

}
