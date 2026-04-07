package account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AccountDTO> create(@PathVariable UUID userId) {

        Account account = accountService.createAccount(userId);

        return ResponseEntity
                .created(URI.create("/accounts/" + account.getId()))
                .body(AccountDTO.from(account));
    }
}