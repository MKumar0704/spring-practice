package com.example.demo.services.impl;

import com.example.demo.domain.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    BookServiceImpl(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }


    @Override
    public BookEntity createUpdateBook(String isbn,BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
       return StreamSupport.stream(bookRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
       return bookRepository.findAll(pageable);
    }


    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExistsById(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity patchUpdate(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
            Optional.ofNullable(bookEntity.getAuthorEntity()).ifPresent(existingBook::setAuthorEntity);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }

}
