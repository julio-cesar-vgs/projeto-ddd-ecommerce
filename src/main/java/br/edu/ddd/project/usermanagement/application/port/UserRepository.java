package br.edu.ddd.project.usermanagement.application.port;

import br.edu.ddd.project.usermanagement.domain.model.aggregateRoots.User;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.Email;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
    User save(User user);

    Optional<User> findById(UserId id);

    boolean existsByEmail(Email email);

    Optional<User> findByEmail(Email email);

    void delete(User user);
}

