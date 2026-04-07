package transaction;

import account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    private LocalDateTime createdAt;

    private String description;

    public Transaction(TransactionType type, BigDecimal amount,
                       TransactionStatus status, Account sourceAccount,
                       Account destinationAccount, String description) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        this.type = type;
        this.amount = amount;
        this.status = status != null ? status : TransactionStatus.PENDING;
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.createdAt = LocalDateTime.now();
        this.description = description;
    }

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED
    }
}