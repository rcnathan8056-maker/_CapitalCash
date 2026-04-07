package account;


import java.util.UUID;

public record AccountDTO(
        UUID id,
        String accountNumber,
        AccountType type,
        AccountStatus status,
        UUID userId
) {
    public static AccountDTO from(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getType(),
                account.getStatus(),
                account.getUser().getId()
        );
    }
}