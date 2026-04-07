package user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO userDTO) {

        User userToCreate = userDTO.toEntity();
        User created = userService.createUser(userToCreate);

        return ResponseEntity.status(201).body(UserDTO.fromEntity(created));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAllUsers();

        List<UserDTO> safeUsers = users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(safeUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable UUID id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }


    public record UserDTO(
            UUID id,
            String name,
            String email,
            String cpf,
            Role role,
            java.time.LocalDateTime createdAt
    ) {
        public static UserDTO fromEntity(User user) {
            return new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getRole(),
                    user.getCreatedAt()
            );
        }
    }

    public record UserCreateDTO(

            @NotBlank
            String name,

            @Email
            String email,

            @NotBlank
            String password,

            @Size(min = 11, max = 11)
            String cpf,

            Role role
    ) {
        public User toEntity() {
            return new User(
                    name,
                    email,
                    password,
                    cpf,
                    role
            );
        }
    }
}