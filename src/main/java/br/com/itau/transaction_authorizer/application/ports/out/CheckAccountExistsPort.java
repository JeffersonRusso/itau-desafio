package br.com.itau.transaction_authorizer.application.ports.out;

import java.util.UUID;

public interface CheckAccountExistsPort {
    boolean exists(UUID accountId);
}
