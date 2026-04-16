package br.com.itau.transaction_authorizer.model.transaction;

import br.com.itau.transaction_authorizer.model.money.Money;
import br.com.itau.transaction_authorizer.model.operation.OperationType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Transaction(
        UUID id,
        UUID accountId,
        OperationType operationType,
        Money money,
        TransactionStatus status,
        OffsetDateTime createdAt
) {
}
