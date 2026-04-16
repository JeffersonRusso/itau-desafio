package br.com.itau.transaction_authorizer.adapters.in.rest.handler;

import br.com.itau.transaction_authorizer.model.exception.AccountNotFoundException;
import br.com.itau.transaction_authorizer.model.exception.CurrencyMismatchException;
import br.com.itau.transaction_authorizer.model.exception.InsufficientBalanceException;
import br.com.itau.transaction_authorizer.model.exception.InvalidTransactionAmountException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFound(AccountNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            InsufficientBalanceException.class,
            CurrencyMismatchException.class,
            InvalidTransactionAmountException.class
    })
    public ResponseEntity<Object> handleUnprocessableEntity(RuntimeException ex, WebRequest request) {
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return buildErrorResponse(ex, "Erro de validação na requisição.", HttpStatus.BAD_REQUEST, errors);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, String message, HttpStatus status) {
        return buildErrorResponse(ex, message, status, null);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception ex, String message, HttpStatus status, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        if (errors != null && !errors.isEmpty()) {
            body.put("errors", errors);
        }
        return new ResponseEntity<>(body, status);
    }
}
