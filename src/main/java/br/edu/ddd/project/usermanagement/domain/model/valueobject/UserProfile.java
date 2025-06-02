package br.edu.ddd.project.usermanagement.domain.model.valueobject;

import java.time.LocalDate;
import java.util.Objects;

public record UserProfile(
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate
) {
    public UserProfile {
        Objects.requireNonNull(firstName, "First name cannot be null");
        Objects.requireNonNull(lastName, "Last name cannot be null");
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
