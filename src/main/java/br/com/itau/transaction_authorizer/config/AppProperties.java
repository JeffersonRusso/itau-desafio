package br.com.itau.transaction_authorizer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    @NotBlank(message = "A moeda padrão (app.default-currency) não pode ser vazia.")
    @Size(min = 3, max = 3, message = "A moeda padrão (app.default-currency) deve ter 3 caracteres.")
    private String defaultCurrency;

    private Sqs sqs = new Sqs();

    @Getter
    @Setter
    public static class Sqs {
        private Queue queue = new Queue();
    }

    @Getter
    @Setter
    public static class Queue {
        @NotBlank(message = "O nome da fila 'account-created' (app.sqs.queue.account-created) não pode ser vazio.")
        private String accountCreated;
    }
}
