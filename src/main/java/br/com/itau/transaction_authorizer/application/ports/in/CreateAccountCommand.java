package br.com.itau.transaction_authorizer.application.ports.in;

import java.util.UUID;

public record CreateAccountCommand(
        UUID accountId,
        UUID owner,
        String status,
        Long createdAt
) {
}
