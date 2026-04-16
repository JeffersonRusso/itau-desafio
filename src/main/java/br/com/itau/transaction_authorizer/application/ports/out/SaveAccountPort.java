package br.com.itau.transaction_authorizer.application.ports.out;

import br.com.itau.transaction_authorizer.model.account.Account;

public interface SaveAccountPort {
    void save(Account account);
}
