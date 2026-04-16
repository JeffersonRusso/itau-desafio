package br.com.itau.transaction_authorizer.model.operation;

import br.com.itau.transaction_authorizer.model.exception.InsufficientBalanceException;
import br.com.itau.transaction_authorizer.model.money.Money;

public interface TransactionOperation {
    Money calculateNewBalance(Money currentBalance, Money transactionAmount) throws InsufficientBalanceException;
}
