package transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionLog {

    private final UUID id;
    private final UUID transactionId;
    private final String message;
    private final LocalDateTime createdAt;

    public TransactionLog(UUID transactionId, String message) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction ID não pode ser nulo");
        }
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Mensagem do log não pode ser vazia");
        }

        this.id = UUID.randomUUID();
        this.transactionId = transactionId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "id=" + id +
                ", transactionId=" + transactionId +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}