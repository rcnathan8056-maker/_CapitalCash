package transaction;

import account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findBySourceAccount(Account account);
    List<Transaction> findByDestinationAccount(Account account);
    List<Transaction> findBySourceAccountAndDestinationAccount(Account source, Account destination);
    List<Transaction> findByType(TransactionType type);
}