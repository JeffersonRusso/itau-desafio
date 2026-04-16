package br.com.itau.transaction_authorizer.adapters.in.rest;

import br.com.itau.transaction_authorizer.adapters.in.rest.dto.request.TransactionRequest;
import br.com.itau.transaction_authorizer.adapters.in.rest.dto.response.TransactionResponse;
import br.com.itau.transaction_authorizer.adapters.in.rest.mapper.TransactionRequestMapper;
import br.com.itau.transaction_authorizer.adapters.in.rest.mapper.TransactionRestMapper;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizeTransactionCommand;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizeTransactionUseCase;
import br.com.itau.transaction_authorizer.application.ports.in.AuthorizationResult;
import br.com.itau.transaction_authorizer.model.transaction.TransactionStatus;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final AuthorizeTransactionUseCase authorizeTransactionUseCase;
    private final TransactionRequestMapper requestMapper;
    private final TransactionRestMapper transactionRestMapper;

    public TransactionController(AuthorizeTransactionUseCase authorizeTransactionUseCase,
                                 TransactionRequestMapper requestMapper,
                                 TransactionRestMapper transactionRestMapper) {
        this.authorizeTransactionUseCase = authorizeTransactionUseCase;
        this.requestMapper = requestMapper;
        this.transactionRestMapper = transactionRestMapper;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionRequest request) {

        log.info("Recebida requisição de transação (accountId: {}, tipo: {}, valor: {}, moeda: {})",
                 request.accountId(), request.operationType(), request.amount(), request.currency());

        AuthorizeTransactionCommand command = requestMapper.toCommand(request);
        AuthorizationResult result = authorizeTransactionUseCase.authorize(command);
        TransactionResponse response = transactionRestMapper.toResponse(result);

        if (result.transaction().status() == TransactionStatus.SUCCEEDED) {
            log.info("Transação SUCCEEDED gerada com sucesso (transactionId: {})", result.transaction().id());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            log.info("Transação FAILED (transactionId: {})", result.transaction().id());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
    }
}
