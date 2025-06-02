package br.edu.ddd.project.usermanagement.application.usecase;

import br.edu.ddd.project.usermanagement.application.dto.response.UserResponse;
import br.edu.ddd.project.usermanagement.application.exception.UserNotFoundException;
import br.edu.ddd.project.usermanagement.application.port.UserRepository;
import br.edu.ddd.project.usermanagement.domain.model.agregateRoots.User;
import br.edu.ddd.project.usermanagement.domain.model.enums.Role;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse execute(String userId) {
        UserId id = new UserId(UUID.fromString(userId));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        return mapToResponse(user);
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