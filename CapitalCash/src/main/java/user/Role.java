package user;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum Role {
    ADMIN("Administrador", EnumSet.of(Permission.DELETE_USER)),
    USER("Usuário", EnumSet.noneOf(Permission.class));

    private final String description;
    private final Set<Permission> permissions;

    Role(String description, Set<Permission> permissions) {
        this.description = description;

        this.permissions = Collections.unmodifiableSet(permissions);
    }

    public String getDescription() {
        return description;
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public Set<Permission> getPermissions() {

        return permissions;
    }

    public enum Permission {
        DELETE_USER
    }

    public static Role fromString(String roleName) {
        try {
            return Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {

            return USER;
        }
    }
}