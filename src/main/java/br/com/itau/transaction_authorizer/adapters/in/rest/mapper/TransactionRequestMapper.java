package br.com.itau.transaction_authorizer.adapters.in.rest.mapper;

import br.com.itau.transaction_authorizer.adapters.in.rest.dto.request.TransactionRequest;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizeTransactionCommand;
import br.com.itau.transaction_authorizer.model.money.Money;
import org.springframework.stereotype.Component;

@Component
public class TransactionRequestMapper {

    public AuthorizeTransactionCommand toCommand(TransactionRequest request) {
        if (request == null) {
            return null;
        }
        Money money = new Money(request.amount(), request.currency());
        return new AuthorizeTransactionCommand(
                request.accountId(),
                request.operationType(),
                money
        );
    }
}
