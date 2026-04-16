package br.com.itau.transaction_authorizer.application.services;

import br.com.itau.transaction_authorizer.application.ports.in.CreateAccountCommand;
import br.com.itau.transaction_authorizer.application.ports.in.CreateAccountUseCase;
import br.com.itau.transaction_authorizer.application.ports.out.CheckAccountExistsPort;
import br.com.itau.transaction_authorizer.application.ports.out.SaveAccountPort;
import br.com.itau.transaction_authorizer.config.AppProperties;
import br.com.itau.transaction_authorizer.model.account.Account;
import br.com.itau.transaction_authorizer.model.money.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountManagementService implements CreateAccountUseCase {

    private final CheckAccountExistsPort checkAccountExistsPort;
    private final SaveAccountPort saveAccountPort;
    private final AppProperties appProperties;

    public AccountManagementService(
            CheckAccountExistsPort checkAccountExistsPort,
            SaveAccountPort saveAccountPort,
            AppProperties appProperties) {
        this.checkAccountExistsPort = checkAccountExistsPort;
        this.saveAccountPort = saveAccountPort;
        this.appProperties = appProperties;
    }

    @Override
    @Transactional
    public void createAccount(CreateAccountCommand command) {
        if (!checkAccountExistsPort.exists(command.accountId())) {
            Money initialBalance = new Money(
                    BigDecimal.ZERO,
                    appProperties.getDefaultCurrency());
            Account newAccount = new Account(
                    command.accountId(),
                    command.owner(),
                    initialBalance,
                    command.status(),
                    command.createdAt());
            saveAccountPort.save(newAccount);
        }
    }
}
