package user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    public User(String name, String email, String passwordHash, String cpf, Role role) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        if (cpf == null || cpf.length() < 11) {
            throw new IllegalArgumentException("CPF inválido");
        }

        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash inválido");
        }

        this.name = name;
        this.email = email.toLowerCase().trim();
        this.passwordHash = passwordHash;
        this.cpf = cpf;
        this.role = role != null ? role : Role.USER;
        this.createdAt = LocalDateTime.now();
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}