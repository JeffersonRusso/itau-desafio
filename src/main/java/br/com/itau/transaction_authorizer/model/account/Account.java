package br.com.itau.transaction_authorizer.model.account;

import br.com.itau.transaction_authorizer.model.exception.InvalidTransactionAmountException;
import br.com.itau.transaction_authorizer.model.operation.OperationType;
import br.com.itau.transaction_authorizer.model.money.Money;
import br.com.itau.transaction_authorizer.model.operation.TransactionOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Account {

    private UUID id;
    private UUID owner;
    private Money balance;
    private String status;
    private Long createdAt;
    private Long version;

    public Account(UUID id, UUID owner, Money balance, String status, Long createdAt) {
        this.id = id;
        this.owner = Objects.requireNonNull(owner, "O proprietário da conta não pode ser nulo.");
        this.balance = Objects.requireNonNull(balance, "O saldo não pode ser nulo");
        if (balance.amount().signum() < 0) {
            throw new InvalidTransactionAmountException("O valor do saldo não pode ser negativo");
        }
        this.status = Objects.requireNonNull(status, "O status da conta não pode ser nulo.");
        this.createdAt = Objects.requireNonNull(createdAt, "A data de criação da conta não pode ser nula.");
    }

    public void process(OperationType operationType, Money transactionAmount) {
        validateAmount(transactionAmount);

        TransactionOperation strategy = operationType.getStrategy();
        this.balance = strategy.calculateNewBalance(this.balance, transactionAmount);
    }

    private void validateAmount(Money amount) {
        Objects.requireNonNull(amount, "O valor da operação não pode ser nulo");
        if (amount.amount().signum() <= 0) {
            throw new InvalidTransactionAmountException("O valor da operação deve ser maior que zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
