package account;

import java.math.BigDecimal;

public enum AccountType {

    CHECKING("Conta Corrente", BigDecimal.ZERO),
    SAVINGS("Conta Poupança", new BigDecimal("0.02"));

    private final String description;
    private final BigDecimal interestRate;

    AccountType(String description, BigDecimal interestRate) {
        this.description = description;
        this.interestRate = interestRate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }


    public BigDecimal calculateInterest(BigDecimal balance, int months) {
        if (balance == null || balance.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;
        return balance.multiply(interestRate).multiply(new BigDecimal(months)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
