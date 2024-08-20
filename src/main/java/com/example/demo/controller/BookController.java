package com.example.demo.controller;

import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entity.BookEntity;
import com.example.demo.mapper.Mapper;
import com.example.demo.repository.BookRepository;
import com.example.demo.services.BookService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class BookController {

    private BookService bookService;

    private Mapper<BookEntity,BookDto> bookMapper;

    public BookController(BookService bookService,Mapper<BookEntity,BookDto> bookMapper){
        this.bookService=bookService;
        this.bookMapper=bookMapper;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn")String isbn,@RequestBody BookDto bookDto){
            log.info(bookDto.toString());
            BookEntity bookEntity=bookMapper.mapFrom(bookDto);
            log.info(bookEntity.toString());
            boolean bookExists=bookService.isExistsById(isbn);
            BookEntity book=bookService.createUpdateBook(isbn,bookEntity);
            if(bookExists){
                return new ResponseEntity<>(bookMapper.mapTo(book), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(bookMapper.mapTo(book), HttpStatus.CREATED);
            }

    }


    @GetMapping(path = "/books")
    public Page<BookDto> getAllBooks(Pageable pageable){
        Page<BookEntity> book=bookService.findAll(pageable);
        return book.map(bookMapper::mapTo);
    }

    @GetMapping(path="/books/{isbn}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("isbn") String isbn){
        Optional<BookEntity> findedBook = bookService.findOne(isbn);
        return findedBook.map(bookEntity -> {
            BookDto bookDto=bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse(
                 new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }


    @PatchMapping(path="/books/{isbn}")
    public ResponseEntity<BookDto> partialBookUpdate(@PathVariable("isbn") String isbn,@RequestBody BookDto bookDto){
       if(!bookService.isExistsById(isbn)){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

        BookEntity bookEntity=bookMapper.mapFrom(bookDto);
        BookEntity updatedBook = bookService.patchUpdate(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBook),HttpStatus.OK);
    }

    @DeleteMapping(path="/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
