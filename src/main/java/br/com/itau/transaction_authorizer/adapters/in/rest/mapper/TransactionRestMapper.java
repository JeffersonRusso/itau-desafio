package br.com.itau.transaction_authorizer.adapters.in.rest.mapper;

import br.com.itau.transaction_authorizer.adapters.in.rest.dto.response.TransactionResponse;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizationResult;
import org.springframework.stereotype.Component;

@Component
public class TransactionRestMapper {

    public TransactionResponse toResponse(AuthorizationResult result) {
        if (result == null || result.transaction() == null || result.account() == null) {
            return null;
        }

        TransactionResponse.TransactionDetails transactionDetails = new TransactionResponse.TransactionDetails(
                result.transaction().id(),
                result.transaction().operationType(),
                new TransactionResponse.AmountResponse(
                        result.transaction().money().amount(),
                        result.transaction().money().currency()),
                result.transaction().status(),
                result.transaction().createdAt()
        );

        TransactionResponse.AccountDetails accountDetails = new TransactionResponse.AccountDetails(
                result.account().getId(),
                new TransactionResponse.BalanceResponse(
                        result.account().getBalance().amount(),
                        result.account().getBalance().currency())
        );

        return new TransactionResponse(transactionDetails, accountDetails);
    }
}
