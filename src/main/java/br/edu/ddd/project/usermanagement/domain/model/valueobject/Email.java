package br.edu.ddd.project.usermanagement.domain.model.valueobject;

import java.util.Objects;

public record Email(String value) {
    public Email {
        Objects.requireNonNull(value, "Email cannot be null");
        if (!isValidEmail(value)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}