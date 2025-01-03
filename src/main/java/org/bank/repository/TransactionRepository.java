package org.bank.repository;

import org.bank.model.Transaction;
import java.util.List;

public interface TransactionRepository extends BaseRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountId(Long accountId);
    List<Transaction> findByTargetAccountId(Long accountId);
    List<Transaction> findBySourceAccountIdOrTargetAccountId(Long sourceId, Long targetId);
}
