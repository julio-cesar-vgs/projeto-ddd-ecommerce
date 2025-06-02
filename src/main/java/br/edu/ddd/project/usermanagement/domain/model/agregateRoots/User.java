package br.edu.ddd.project.usermanagement.domain.model.agregateRoots;

import br.edu.ddd.project.usermanagement.domain.model.enums.Role;
import br.edu.ddd.project.usermanagement.domain.model.enums.UserStatus;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.Email;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.UserId;
import br.edu.ddd.project.usermanagement.domain.model.valueobject.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private UserId id;
    private Email email;
    private String passwordHash;
    private UserProfile profile;
    private Set<Role> roles;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User create(Email email, String passwordHash, UserProfile profile, Set<Role> roles) {
        return User.builder().id(UserId.generate()).email(email).passwordHash(passwordHash).profile(profile).roles(roles).status(UserStatus.PENDING_VERIFICATION).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).build();
    }

    public void activate() {
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void suspend() {
        this.status = UserStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }
}

