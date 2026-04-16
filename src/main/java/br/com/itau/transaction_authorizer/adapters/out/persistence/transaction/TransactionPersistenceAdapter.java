package br.com.itau.transaction_authorizer.adapters.out.persistence.transaction;

import br.com.itau.transaction_authorizer.adapters.out.persistence.mapper.TransactionPersistenceMapper;
import br.com.itau.transaction_authorizer.application.ports.out.SaveTransactionPort;
import br.com.itau.transaction_authorizer.model.transaction.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionPersistenceAdapter implements SaveTransactionPort {

    private final JpaTransactionRepository transactionRepository;
    private final TransactionPersistenceMapper mapper;

    public TransactionPersistenceAdapter(JpaTransactionRepository transactionRepository, TransactionPersistenceMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Transaction transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        transactionRepository.save(entity);
    }
}
