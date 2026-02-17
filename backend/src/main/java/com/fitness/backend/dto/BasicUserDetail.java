package com.fitness.backend.dto;

public record BasicUserDetail(
    Long id,
    String role,
    UserProfileDetail userProfileDetail) {
}