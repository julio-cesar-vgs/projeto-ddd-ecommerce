package br.edu.ddd.project.usermanagement.application.usecase;

import java.util.UUID;

public record UserCreatedEvent(UUID userId, String email, String firstName, String lastName) {}
