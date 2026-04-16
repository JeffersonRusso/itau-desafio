package br.com.itau.transaction_authorizer.model.operation;

import lombok.Getter;

@Getter
public enum OperationType {
    CREDIT(new CreditOperation()),
    DEBIT(new DebitOperation());

    private final TransactionOperation strategy;

    OperationType(TransactionOperation strategy) {
        this.strategy = strategy;
    }
}
