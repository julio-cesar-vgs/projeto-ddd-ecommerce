package br.edu.ddd.project.usermanagement.application.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        Set<String> roles,
        String status,
        LocalDateTime createdAt
) {}
