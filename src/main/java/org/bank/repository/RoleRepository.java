package org.bank.repository;

import org.bank.model.Role;
import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
