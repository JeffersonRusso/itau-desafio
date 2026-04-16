package br.com.itau.transaction_authorizer.model.exception;

public class InvalidTransactionAmountException extends IllegalArgumentException {
    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}
