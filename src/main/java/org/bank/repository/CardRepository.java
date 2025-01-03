package org.bank.repository;

import org.bank.model.Card;
import java.util.Optional;

public interface CardRepository extends BaseRepository<Card, Long> {
    Optional<Card> findByCardNumber(String cardNumber);
    Optional<Card> findByAccountId(Long accountId);
}
