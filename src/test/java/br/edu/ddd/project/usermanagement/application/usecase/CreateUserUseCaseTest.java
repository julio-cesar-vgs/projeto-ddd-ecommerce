package br.edu.ddd.project.usermanagement.application.usecase;

import br.edu.ddd.project.usermanagement.application.dto.request.CreateUserRequest;
import br.edu.ddd.project.usermanagement.application.dto.response.UserResponse;
import br.edu.ddd.project.usermanagement.application.exception.UserAlreadyExistsException;
import br.edu.ddd.project.usermanagement.application.port.UserRepository;
import br.edu.ddd.project.usermanagement.application.service.DomainEventPublisher;
import br.edu.ddd.project.usermanagement.domain.model.agregateRoots.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private DomainEventPublisher eventPublisher;
    @InjectMocks
    private CreateUserUseCase useCase;

    @Test
    void createsUserAndPublishesEvent() {
        CreateUserRequest request = new CreateUserRequest(
                "test@example.com",
                "pass",
                "John",
                "Doe",
                null,
                null,
                Set.of("USER")
        );
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserResponse response = useCase.execute(request);

        assertEquals("test@example.com", response.email());
        verify(eventPublisher).publish(any(UserCreatedEvent.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void throwsWhenEmailExists() {
        CreateUserRequest request = new CreateUserRequest(
                "test@example.com",
                "pass",
                "John",
                "Doe",
                null,
                null,
                Set.of("USER")
        );
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> useCase.execute(request));
    }
}
