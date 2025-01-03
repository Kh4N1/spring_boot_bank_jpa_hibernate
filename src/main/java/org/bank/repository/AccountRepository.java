package org.bank.repository;

import org.bank.model.Account;
import org.bank.model.enums.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account, Long> {
  Optional<Account> findByAccountNumber(String accountNumber);

  List<Account> findByUserId(Long userId);

  @Query("SELECT a FROM Account a WHERE a.user.id = :userId AND a.currency = :currency")
  List<Account> findByUserIdAndCurrency(
      @Param("userId") Long userId, @Param("currency") Currency currency);
}
