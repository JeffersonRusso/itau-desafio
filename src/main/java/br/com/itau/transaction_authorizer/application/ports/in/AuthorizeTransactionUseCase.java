package br.com.itau.transaction_authorizer.application.ports.in;

public interface AuthorizeTransactionUseCase {
    AuthorizationResult authorize(AuthorizeTransactionCommand command);
}
