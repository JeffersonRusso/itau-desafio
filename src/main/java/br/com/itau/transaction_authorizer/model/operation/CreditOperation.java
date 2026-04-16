package br.com.itau.transaction_authorizer.model.operation;

import br.com.itau.transaction_authorizer.model.money.Money;

public class CreditOperation implements TransactionOperation {
    @Override
    public Money calculateNewBalance(Money currentBalance, Money transactionAmount) {
        return currentBalance.add(transactionAmount);
    }
}
