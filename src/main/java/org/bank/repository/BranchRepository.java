package org.bank.repository;

import org.bank.model.Branch;
import java.util.Optional;

public interface BranchRepository extends BaseRepository<Branch, Long> {
    Optional<Branch> findByCode(String code);
}
