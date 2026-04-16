package br.com.itau.transaction_authorizer.application.ports.out;

import br.com.itau.transaction_authorizer.model.account.Account;
import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {
    Optional<Account> loadAccountWithLock(UUID accountId);
}
