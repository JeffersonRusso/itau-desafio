package br.com.itau.transaction_authorizer.model.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(UUID accountId) {
        super("Conta com o ID " + accountId + " não foi encontrada.");
    }
}
