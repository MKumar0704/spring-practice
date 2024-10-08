package com.example.demo.services;

import com.example.demo.domain.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExistsById(Long id);

    AuthorEntity partialUpdate(Long id,AuthorEntity authorEntity);

    void delete(Long id);
}
