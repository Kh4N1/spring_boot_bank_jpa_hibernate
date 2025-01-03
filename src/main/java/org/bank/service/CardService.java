package org.bank.service;

import lombok.extern.slf4j.Slf4j;
import org.bank.model.Card;
import org.bank.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Slf4j
@Transactional
public class CardService extends BaseService<Card, Long> {
    
    private final CardRepository cardRepository;
    
    public CardService(CardRepository cardRepository) {
        super(cardRepository);
        this.cardRepository = cardRepository;
    }
    
    public Card findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
            .orElseThrow(() -> new EntityNotFoundException("Card not found with number: " + cardNumber));
    }
    
    public Card findByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId)
            .orElseThrow(() -> new EntityNotFoundException("Card not found for account: " + accountId));
    }
}
