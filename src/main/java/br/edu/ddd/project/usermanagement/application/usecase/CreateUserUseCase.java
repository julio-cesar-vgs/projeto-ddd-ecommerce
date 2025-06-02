package br.edu.ddd.project.usermanagement.application.usecase;

import br.edu.ddd.project.usermanagement.application.dto.request.CreateUserRequest;
import br.edu.ddd.project.usermanagement.application.dto.response.UserResponse;
import br.edu.ddd.project.usermanagement.application.exception.UserNotFoundException;
import br.edu.ddd.project.usermanagement.application.port.UserRepository;
import br.edu.ddd.project.usermanagement.application.service.DomainEventPublisher;
import br.edu.ddd.project.usermanagement.domain.model.agregateRoots.User;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.*;
import br.edu.ddd.project.usermanagement.domain.model.enums.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final DomainEventPublisher eventPublisher;

    public CreateUserUseCase(UserRepository userRepository, DomainEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public UserResponse execute(CreateUserRequest request) {
        // Validação de regras de negócio
        Email email = new Email(request.email());
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email already exists: " + request.email());
        }

        // Criação dos value objects
        UserProfile profile = new UserProfile(
                request.firstName(),
                request.lastName(),
                request.phoneNumber(),
                request.birthDate()
        );

        Set<Role> roles = request.roles().stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        // Criação do agregado
        User user = User.create(email, request.password(), profile, roles);

        // Persistência
        User savedUser = userRepository.save(user);

        // Publicação de eventos
        eventPublisher.publish(new UserCreatedEvent(
                savedUser.getId().value(),
                savedUser.getEmail().value(),
                savedUser.getProfile().firstName(),
                savedUser.getProfile().lastName()
        ));

        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId().value().toString(),
                user.getEmail().value(),
                user.getProfile().firstName(),
                user.getProfile().lastName(),
                user.getProfile().phoneNumber(),
                user.getProfile().birthDate(),
                user.getRoles().stream().map(Role::name).collect(Collectors.toSet()),
                user.getStatus().name(),
                user.getCreatedAt()
        );
    }
}

