package org.bank.service;

import lombok.extern.slf4j.Slf4j;
import org.bank.model.Transaction;
import org.bank.model.enums.TransactionStatus;
import org.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class TransactionService extends BaseService<Transaction, Long> {
    
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }
    
    public List<Transaction> getAccountTransactions(Long accountId) {
        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
    
    @Override
    public Transaction save(Transaction transaction) {
        try {
            accountService.transfer(
                transaction.getSourceAccount().getAccountNumber(),
                transaction.getTargetAccount().getAccountNumber(),
                transaction.getAmount()
            );
            transaction.setStatus(TransactionStatus.COMPLETED);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            log.error("Transaction failed", e);
        }
        return transactionRepository.save(transaction);
    }
}
