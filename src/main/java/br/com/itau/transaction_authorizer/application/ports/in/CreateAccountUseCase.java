package br.com.itau.transaction_authorizer.application.ports.in;

public interface CreateAccountUseCase {
    void createAccount(CreateAccountCommand command);
}
