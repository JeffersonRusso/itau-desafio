package br.com.itau.transaction_authorizer.adapters.out.persistence.account;

import br.com.itau.transaction_authorizer.adapters.out.persistence.mapper.AccountPersistenceMapper;
import br.com.itau.transaction_authorizer.application.ports.out.CheckAccountExistsPort;
import br.com.itau.transaction_authorizer.application.ports.out.LoadAccountPort;
import br.com.itau.transaction_authorizer.application.ports.out.SaveAccountPort;
import br.com.itau.transaction_authorizer.model.account.Account;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort, CheckAccountExistsPort {

    private final JpaAccountRepository accountRepository;
    private final AccountPersistenceMapper mapper;

    public AccountPersistenceAdapter(JpaAccountRepository accountRepository, AccountPersistenceMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> loadAccountWithLock(UUID accountId) {
        return accountRepository.findByIdWithLock(accountId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional
    public void save(Account account) {
        AccountEntity entity = mapper.toEntity(account);
        accountRepository.save(entity);
    }

    @Override
    public boolean exists(UUID accountId) {
        return accountRepository.existsById(accountId);
    }
}
