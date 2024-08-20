package com.example.demo.services;

import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity createUpdateBook(String isbn,BookEntity bookEntity);

    List<BookEntity> findAll();

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    boolean isExistsById(String isbn);

    BookEntity patchUpdate(String isbn, BookEntity bookEntity);

    void delete(String isbn);
}
