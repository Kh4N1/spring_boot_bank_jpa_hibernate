package org.bank.service;

import org.bank.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

public abstract class BaseService<T, ID> {
    
    protected final BaseRepository<T, ID> repository;
    
    protected BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }
    
    public T save(T entity) {
        return repository.save(entity);
    }
    
    public T findById(ID id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }
    
    public List<T> findAll() {
        return repository.findAll();
    }
    
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
