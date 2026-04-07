package account;

import org.springframework.stereotype.Service;
import user.User;
import user.UserRepository;
import java.security.SecureRandom;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Account account = new Account(generateSecureAccountNumber(), AccountType.CHECKING, user);

        return accountRepository.save(account);
    }

    private String generateSecureAccountNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        String accountNumber = sb.toString();
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            return generateSecureAccountNumber();
        }
        return accountNumber;
    }
}