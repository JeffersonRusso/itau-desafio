package br.com.itau.transaction_authorizer.adapters.in.sqs.mapper;

import br.com.itau.transaction_authorizer.adapters.in.sqs.dto.AccountPayload;
import br.com.itau.transaction_authorizer.application.ports.in.CreateAccountCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountSqsMapper {

    public CreateAccountCommand toCreateAccountCommand(AccountPayload accountPayload) {
        if (accountPayload == null) {
            return null;
        }
        return new CreateAccountCommand(
                accountPayload.id(),
                accountPayload.owner(),
                accountPayload.status(),
                accountPayload.createdAt()
        );
    }
}
