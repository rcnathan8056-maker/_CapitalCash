package transaction;

import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("Depósito"),
    WITHDRAW("Saque"),
    TRANSFER("Transferência");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }


    public boolean isDeposit() {
        return this == DEPOSIT;
    }

    public boolean isWithdraw() {
        return this == WITHDRAW;
    }

    public boolean isTransfer() {
        return this == TRANSFER;
    }
}
