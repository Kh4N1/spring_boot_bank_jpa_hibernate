package org.bank.service;

import lombok.extern.slf4j.Slf4j;
import org.bank.model.Account;
import org.bank.model.enums.Currency;
import org.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AccountService extends BaseService<Account, Long> {
    
    private final AccountRepository accountRepository;
    
    public AccountService(AccountRepository accountRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }
    
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new EntityNotFoundException("Account not found with number: " + accountNumber));
    }
    
    public List<Account> findByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }
    
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = findByAccountNumber(fromAccountNumber);
        Account toAccount = findByAccountNumber(toAccountNumber);
        
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
