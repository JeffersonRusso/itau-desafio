package br.com.itau.transaction_authorizer.model.exception;

public class CurrencyMismatchException extends IllegalArgumentException {
    public CurrencyMismatchException(String currency, String other) {
        super("Não é possível operar com moedas diferentes (" + currency + " e " + other + ").");
    }
}
