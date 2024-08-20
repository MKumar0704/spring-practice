package com.example.demo;

import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.domain.entity.BookEntity;

import java.awt.print.Book;

public class TestDataUtil {

    private TestDataUtil(){
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("baymax")
                .age(34)
                .build();
    }

    public static AuthorDto createAuthorDto() {
        return AuthorDto.builder()
                .id(1L)
                .name("baymax")
                .age(34)
                .build();
    }

    public static AuthorDto createAuthorDtoB() {
        return AuthorDto.builder()
                .id(2L)
                .name("Rogers")
                .age(28)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Rogers")
                .age(28)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Maxi")
                .age(30)
                .build();
    }


    public static BookEntity createTestBookA(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("1234")
                .title("Title for Book A")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookDto createBookDtoA(AuthorDto authorEntity) {
        return BookDto.builder()
                .isbn("1234")
                .title("Title for Book A")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("3456")
                .title("Title for Book B")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("7890")
                .title("Title for Book C")
                .authorEntity(authorEntity)
                .build();
    }
}
