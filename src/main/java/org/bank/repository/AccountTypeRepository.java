package org.bank.repository;

import org.bank.model.AccountType;
import java.util.Optional;

public interface AccountTypeRepository extends BaseRepository<AccountType, Long> {
  Optional<AccountType> findByName(String name);
}