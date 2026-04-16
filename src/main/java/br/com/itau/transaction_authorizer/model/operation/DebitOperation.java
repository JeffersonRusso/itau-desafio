package br.com.itau.transaction_authorizer.model.operation;

import br.com.itau.transaction_authorizer.model.exception.InsufficientBalanceException;
import br.com.itau.transaction_authorizer.model.money.Money;

public class DebitOperation implements TransactionOperation {
    @Override
    public Money calculateNewBalance(Money currentBalance, Money transactionAmount) throws InsufficientBalanceException {
        if (currentBalance.isLessThan(transactionAmount)) {
            throw new InsufficientBalanceException("Saldo insuficiente para realizar o débito.");
        }
        return currentBalance.subtract(transactionAmount);
    }
}
