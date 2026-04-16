package br.com.itau.transaction_authorizer.application.ports.out;

import br.com.itau.transaction_authorizer.model.transaction.Transaction;

public interface SaveTransactionPort {
    void save(Transaction transaction);
}
