package br.com.itau.transaction_authorizer.adapters.out.persistence.mapper;

import br.com.itau.transaction_authorizer.adapters.out.persistence.account.AccountEntity;
import br.com.itau.transaction_authorizer.model.account.Account;
import br.com.itau.transaction_authorizer.model.money.Money;
import org.springframework.stereotype.Component;

@Component
public class AccountPersistenceMapper {

    public AccountEntity toEntity(Account account) {
        if (account == null) {
            return null;
        }
        AccountEntity entity = new AccountEntity();
        entity.setId(account.getId());
        entity.setOwner(account.getOwner());
        entity.setBalance(account.getBalance().amount());
        entity.setCurrency(account.getBalance().currency());
        entity.setStatus(account.getStatus());
        entity.setCreatedAt(account.getCreatedAt());
        entity.setVersion(account.getVersion());
        return entity;
    }

    public Account toDomain(AccountEntity entity) {
        if (entity == null) {
            return null;
        }
        Money balance = new Money(
                entity.getBalance(),
                entity.getCurrency());
        Account account = new Account(
                entity.getId(),
                entity.getOwner(),
                balance, entity.getStatus(),
                entity.getCreatedAt());
        account.setVersion(entity.getVersion());
        return account;
    }
}
