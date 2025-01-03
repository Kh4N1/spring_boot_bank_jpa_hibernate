package org.bank.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.bank.model.Branch;
import org.bank.repository.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class BranchService extends BaseService<Branch, Long> {

  private final BranchRepository branchRepository;

  public BranchService(BranchRepository branchRepository) {
    super(branchRepository);
    this.branchRepository = branchRepository;
  }

  public Branch findByCode(String code) {
    return branchRepository.findByCode(code)
        .orElseThrow(() -> new EntityNotFoundException("Branch not found with code: " + code));
  }
}