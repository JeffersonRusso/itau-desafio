package br.com.itau.transaction_authorizer.application.ports.out;

import br.com.itau.transaction_authorizer.model.account.Account;

public interface PublishAccountCreatedPort {
    void publish(Account account);
}
