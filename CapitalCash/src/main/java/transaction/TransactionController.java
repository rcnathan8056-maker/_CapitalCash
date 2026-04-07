package transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> deposit(
            @RequestParam @NotBlank String accountNumber,
            @RequestParam @DecimalMin(value = "0.01", inclusive = true) BigDecimal amount) {

        Transaction transaction = transactionService.deposit(accountNumber, amount);
        return ResponseEntity.ok(TransactionResponseDTO.fromTransaction(transaction, "Depósito realizado"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdraw(
            @RequestParam @NotBlank String accountNumber,
            @RequestParam @DecimalMin(value = "0.01", inclusive = true) BigDecimal amount) {

        Transaction transaction = transactionService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(TransactionResponseDTO.fromTransaction(transaction, "Saque realizado"));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponseDTO> transfer(
            @RequestParam @NotBlank String from,
            @RequestParam @NotBlank String to,
            @RequestParam @DecimalMin(value = "0.01", inclusive = true) BigDecimal amount) {

        Transaction transaction = transactionService.transfer(from, to, amount);
        return ResponseEntity.ok(TransactionResponseDTO.fromTransaction(transaction, "Transferência realizada"));
    }

    public record TransactionResponseDTO(
            String transactionId,
            String type,
            String status,
            BigDecimal amount,
            String message
    ) {
        public static TransactionResponseDTO fromTransaction(Transaction tx, String message) {
            return new TransactionResponseDTO(
                    tx.getId().toString(),
                    tx.getType().name(),
                    tx.getStatus().name(),
                    tx.getAmount(),
                    message
            );
        }
    }
}