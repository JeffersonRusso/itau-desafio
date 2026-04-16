package br.com.itau.transaction_authorizer.application.ports.in;

import br.com.itau.transaction_authorizer.model.account.Account;
import br.com.itau.transaction_authorizer.model.transaction.Transaction;

public record AuthorizationResult(
        Transaction transaction,
        Account account
) {}
