package br.com.itau.transaction_authorizer.application.services;

import br.com.itau.transaction_authorizer.application.ports.in.AuthorizationResult;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizeTransactionCommand;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizeTransactionUseCase;
import br.com.itau.transaction_authorizer.application.ports.out.LoadAccountPort;
import br.com.itau.transaction_authorizer.application.ports.out.SaveAccountPort;
import br.com.itau.transaction_authorizer.application.ports.out.SaveTransactionPort;
import br.com.itau.transaction_authorizer.model.account.Account;
import br.com.itau.transaction_authorizer.model.exception.AccountNotFoundException;
import br.com.itau.transaction_authorizer.model.exception.CurrencyMismatchException;
import br.com.itau.transaction_authorizer.model.exception.InsufficientBalanceException;
import br.com.itau.transaction_authorizer.model.exception.InvalidTransactionAmountException;
import br.com.itau.transaction_authorizer.model.transaction.Transaction;
import br.com.itau.transaction_authorizer.model.transaction.TransactionStatus;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionAuthorizationService implements AuthorizeTransactionUseCase {

    private final LoadAccountPort loadAccountPort;
    private final SaveAccountPort saveAccountPort;
    private final SaveTransactionPort saveTransactionPort;

    public TransactionAuthorizationService(
            LoadAccountPort loadAccountPort,
            SaveAccountPort saveAccountPort,
            SaveTransactionPort saveTransactionPort) {
        this.loadAccountPort = loadAccountPort;
        this.saveAccountPort = saveAccountPort;
        this.saveTransactionPort = saveTransactionPort;
    }

    @Override
    @Transactional
    @Retry(name = "transactionAuthorization")
    public AuthorizationResult authorize(AuthorizeTransactionCommand command) {
        Account account = loadAccountPort.loadAccountWithLock(command.accountId())
                .orElseThrow(() -> new AccountNotFoundException(command.accountId()));

        Transaction transaction;
        try {
            account.process(command.operationType(), command.money());
            saveAccountPort.save(account);
            transaction = command.toTransaction(TransactionStatus.SUCCEEDED);
        } catch (InsufficientBalanceException
                 | CurrencyMismatchException
                 | InvalidTransactionAmountException e) {
            transaction = command.toTransaction(TransactionStatus.FAILED);
        }

        saveTransactionPort.save(transaction);

        return new AuthorizationResult(transaction, account);
    }
}
