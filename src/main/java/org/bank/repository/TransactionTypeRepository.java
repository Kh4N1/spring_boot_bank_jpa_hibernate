package org.bank.repository;

import org.bank.model.TransactionType;
import java.util.Optional;

public interface TransactionTypeRepository extends BaseRepository<TransactionType, Long> {
  Optional<TransactionType> findByName(String name);
}