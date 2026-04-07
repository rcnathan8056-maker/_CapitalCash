package transaction;

import account.Account;
import account.AccountRepository;
import account.AccountStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        validateAmount(amount);

        Account account = getActiveAccount(accountNumber);

        account.deposit(amount); // 🔥 usa regra da entidade

        Transaction tx = new Transaction(
                TransactionType.DEPOSIT,
                amount,
                Transaction.TransactionStatus.COMPLETED,
                null,
                account,
                "Depósito de " + amount
        );

        return transactionRepository.save(tx);
    }

    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        validateAmount(amount);

        Account account = getActiveAccount(accountNumber);

        account.withdraw(amount); // 🔥 usa regra da entidade

        Transaction tx = new Transaction(
                TransactionType.WITHDRAW,
                amount,
                Transaction.TransactionStatus.COMPLETED,
                account,
                null,
                "Saque de " + amount
        );

        return transactionRepository.save(tx);
    }

    @Transactional
    public Transaction transfer(String from, String to, BigDecimal amount) {
        validateAmount(amount);

        if (from.equals(to)) {
            throw new IllegalArgumentException("Transferência para mesma conta não permitida");
        }

        Account source = getActiveAccount(from);
        Account destination = getActiveAccount(to);

        source.withdraw(amount);
        destination.deposit(amount);

        Transaction tx = new Transaction(
                TransactionType.TRANSFER,
                amount,
                Transaction.TransactionStatus.COMPLETED,
                source,
                destination,
                "Transferência de " + amount
        );

        return transactionRepository.save(tx);
    }

    // ================= VALIDATIONS =================

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
    }

    private Account getActiveAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Conta não está ativa");
        }

        return account;
    }
}
