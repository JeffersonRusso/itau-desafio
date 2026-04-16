package br.com.itau.transaction_authorizer.adapters.in.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import br.com.itau.transaction_authorizer.model.operation.OperationType;
import br.com.itau.transaction_authorizer.model.transaction.TransactionStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionResponse(
        TransactionDetails transaction,
        AccountDetails account
) {
    public record TransactionDetails(
            UUID id,
            @JsonProperty("type") OperationType operationType,
            AmountResponse amount,
            TransactionStatus status,
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
            OffsetDateTime timestamp
    ) {}

    public record AmountResponse(
            BigDecimal value,
            String currency
    ) {}

    public record AccountDetails(
            UUID id,
            BalanceResponse balance
    ) {}

    public record BalanceResponse(
            BigDecimal amount,
            String currency
    ) {}
}
