package br.com.itau.transaction_authorizer.model.money;

import br.com.itau.transaction_authorizer.model.exception.CurrencyMismatchException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;


public record Money(
        @NotNull(message = "A quantia não pode ser nula.")
        @Positive(message = "A quantia deve ser positiva.")
        BigDecimal amount,

        @NotBlank(message = "A moeda não pode ser vazia.")
        @Size(min = 3, max = 3, message = "A moeda deve ter 3 caracteres.")
        String currency
) {

    public Money {
        Objects.requireNonNull(amount, "Quantia não pode ser nula.");
        Objects.requireNonNull(currency, "Moeda não pode ser nula.");
        if (currency.isBlank()) {
            throw new IllegalArgumentException("Moeda não pode ser vazia.");
        }
    }

    public Money add(Money other) {
        validateCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        validateCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public boolean isLessThan(Money other) {
        validateCurrency(other);
        return this.amount.compareTo(other.amount) < 0;
    }

    private void validateCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new CurrencyMismatchException(this.currency, other.currency);
        }
    }
}
