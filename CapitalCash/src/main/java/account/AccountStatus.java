package account;

public enum AccountStatus {

    ACTIVE("Ativa"),
    BLOCKED("Bloqueada"),
    CLOSED("Encerrada");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean canOperate() {
        return this == ACTIVE;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }
    public boolean isClosed() {
        return this == CLOSED;
    }
}
