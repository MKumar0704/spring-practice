package com.example.demo.services.impl;

import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.services.AuthorService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
       return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExistsById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        authorEntity.setId(id);
       return authorRepository.findById(id).map(
                existingAuthor -> {
                    Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
                    Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
                    return authorRepository.save(existingAuthor);
                }
        ).orElseThrow(()-> new RuntimeException("Author doesnt exists"));

    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
