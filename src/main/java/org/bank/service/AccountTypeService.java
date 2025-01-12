package org.bank.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bank.model.AccountType;
import org.bank.repository.AccountTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AccountTypeService extends BaseService<AccountType, Long> {

  private final AccountTypeRepository accountTypeRepository;

  public AccountTypeService(AccountTypeRepository accountTypeRepository) {
    super(accountTypeRepository);
    this.accountTypeRepository = accountTypeRepository;
  }

  public AccountType findByName(String name) {
    return accountTypeRepository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException("AccountType not found with name: " + name));
  }
}