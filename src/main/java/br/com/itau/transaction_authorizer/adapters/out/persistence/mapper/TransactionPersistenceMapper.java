package br.com.itau.transaction_authorizer.adapters.out.persistence.mapper;

import br.com.itau.transaction_authorizer.adapters.out.persistence.transaction.TransactionEntity;
import br.com.itau.transaction_authorizer.model.transaction.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionPersistenceMapper {

    public TransactionEntity toEntity(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.id());
        entity.setAccountId(transaction.accountId());
        entity.setOperationType(transaction.operationType());
        entity.setAmount(transaction.money().amount());
        entity.setCurrency(transaction.money().currency());
        entity.setStatus(transaction.status());
        entity.setCreatedAt(transaction.createdAt());
        return entity;
    }
}
