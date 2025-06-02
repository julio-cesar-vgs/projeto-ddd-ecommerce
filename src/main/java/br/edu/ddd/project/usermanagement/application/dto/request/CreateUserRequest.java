package br.edu.ddd.project.usermanagement.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record CreateUserRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String phoneNumber,
        LocalDate birthDate,
        @NotNull Set<String> roles
) {}

