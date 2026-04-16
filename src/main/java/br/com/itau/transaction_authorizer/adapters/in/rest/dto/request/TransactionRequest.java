package br.com.itau.transaction_authorizer.adapters.in.rest.dto.request;

import br.com.itau.transaction_authorizer.model.operation.OperationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
        @NotNull(message = "Account ID is mandatory") UUID accountId,
        @NotNull(message = "Operation Type is mandatory") OperationType operationType,
        @NotNull(message = "Amount is mandatory") @Positive(message = "Amount must be greater than zero") BigDecimal amount,
        @NotNull(message = "Currency is mandatory") String currency
) {}