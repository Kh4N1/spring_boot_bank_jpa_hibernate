package org.bank.repository;

import org.bank.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.accounts WHERE u.id = :id")
    Optional<User> findByIdWithAccounts(@Param("id") Long id);
}
