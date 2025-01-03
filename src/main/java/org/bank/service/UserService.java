package org.bank.service;

import lombok.extern.slf4j.Slf4j;
import org.bank.model.User;
import org.bank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

@Service
@Slf4j
@Transactional
public class UserService extends BaseService<User, Long> {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }
    
    public User findByIdWithAccounts(Long id) {
        return userRepository.findByIdWithAccounts(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}
