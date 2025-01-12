package org.bank.service;

import lombok.extern.slf4j.Slf4j;
import org.bank.model.Account;
import org.bank.model.Transaction;
import org.bank.model.TransactionType;
import org.bank.model.enums.Currency;
import org.bank.model.enums.TransactionStatus;
import org.bank.repository.AccountRepository;
import org.bank.repository.TransactionRepository;
import org.bank.repository.TransactionTypeRepository;
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
  private final TransactionRepository transactionRepository;
  private final TransactionTypeRepository transactionTypeRepository;

  public AccountService(
      AccountRepository accountRepository,
      TransactionRepository transactionRepository,
      TransactionTypeRepository transactionTypeRepository) {
    super(accountRepository);
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.transactionTypeRepository = transactionTypeRepository;
  }

  public Account findByAccountNumber(String accountNumber) {
    return accountRepository
        .findByAccountNumber(accountNumber)
        .orElseThrow(
            () -> new EntityNotFoundException("Account not found with number: " + accountNumber));
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

    // Update balances
    fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
    toAccount.setBalance(toAccount.getBalance().add(amount));

    // Save updated accounts
    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);

    // Get or create transfer transaction type
    TransactionType transferType =
        transactionTypeRepository
            .findByName("TRANSFER")
            .orElseGet(
                () ->
                    transactionTypeRepository.save(
                        TransactionType.builder()
                            .name("TRANSFER")
                            .description("Money transfer between accounts")
                            .build()));

    // Create and save transaction record
    Transaction transaction =
        Transaction.builder()
            .amount(amount)
            .currency(fromAccount.getCurrency())
            .sourceAccount(fromAccount)
            .targetAccount(toAccount)
            .type(transferType)
            .status(TransactionStatus.COMPLETED)
            .description("Transfer from " + fromAccountNumber + " to " + toAccountNumber)
            .referenceNumber(generateReferenceNumber())
            .build();

    transactionRepository.save(transaction);
  }

  private String generateReferenceNumber() {
    return "TRX" + System.currentTimeMillis();
  }
}
