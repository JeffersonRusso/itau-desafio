package br.com.itau.transaction_authorizer.application.ports.in;

import br.com.itau.transaction_authorizer.model.money.Money;
import br.com.itau.transaction_authorizer.model.operation.OperationType;
import br.com.itau.transaction_authorizer.model.transaction.Transaction;
import br.com.itau.transaction_authorizer.model.transaction.TransactionStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AuthorizeTransactionCommand(
        @NotNull(message = "O ID da conta não pode ser nulo.")
        UUID accountId,

        @NotNull(message = "O tipo de operação não pode ser nulo.")
        OperationType operationType,

        @NotNull(message = "O valor da transação não pode ser nulo.")
        @Valid
        Money money
) {
    public Transaction toTransaction(TransactionStatus status) {
        return new Transaction(
                UUID.randomUUID(),
                this.accountId,
                this.operationType,
                this.money,
                status,
                OffsetDateTime.now()
        );
    }
}
