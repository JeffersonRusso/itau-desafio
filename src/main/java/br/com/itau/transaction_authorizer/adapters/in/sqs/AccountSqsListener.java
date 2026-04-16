package br.com.itau.transaction_authorizer.adapters.in.sqs;

import br.com.itau.transaction_authorizer.adapters.in.sqs.dto.AccountCreatedEvent;
import br.com.itau.transaction_authorizer.adapters.in.sqs.mapper.AccountSqsMapper;
import br.com.itau.transaction_authorizer.application.ports.in.CreateAccountCommand;
import br.com.itau.transaction_authorizer.application.ports.in.CreateAccountUseCase;
import br.com.itau.transaction_authorizer.config.CorrelationIdFilter;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class AccountSqsListener {

    private final CreateAccountUseCase createAccountUseCase;
    private final AccountSqsMapper accountSqsMapper;

    public AccountSqsListener(CreateAccountUseCase createAccountUseCase, AccountSqsMapper accountSqsMapper) {
        this.createAccountUseCase = createAccountUseCase;
        this.accountSqsMapper = accountSqsMapper;
    }

    @SqsListener("${app.sqs.queue.account-created:conta-bancaria-criada}")
    public void listen(AccountCreatedEvent event, @Header(value = CorrelationIdFilter.CORRELATION_ID_HEADER, required = false) String correlationId) {
        String currentCorrelationId = correlationId;
        if (currentCorrelationId == null || currentCorrelationId.isEmpty()) {
            currentCorrelationId = UUID.randomUUID().toString();
        }
        MDC.put(CorrelationIdFilter.CORRELATION_ID_MDC_KEY, currentCorrelationId);

        try {
            if (event == null || event.account() == null || event.account().id() == null) {
                log.warn("Mensagem SQS ignorada: payload ou accountId nulo.");
                return;
            }

            log.info("Recebida mensagem SQS para criar conta: {}", event.account().id());

            CreateAccountCommand command = accountSqsMapper.toCreateAccountCommand(event.account());
            createAccountUseCase.createAccount(command);
            log.info("Conta {} processada com sucesso via SQS.", event.account().id());
        } catch (Exception e) {
            UUID accountId = (event != null && event.account() != null) ? event.account().id() : null;
            log.error("Erro ao processar criação de conta via SQS (accountId: {}). Motivo: {}", accountId, e.getMessage(), e);
            throw e;
        } finally {
            MDC.remove(CorrelationIdFilter.CORRELATION_ID_MDC_KEY);
        }
    }
}
