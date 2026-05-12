package com.fitness.backend.dto;

public record UserProfileDetail(
  String name,
  String email,
  String dateOfBirthIso,
  String gender
) {}
