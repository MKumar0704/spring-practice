package com.example.demo.controller;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.domain.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.print.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc,BookService bookService){
        this.mockMvc=mockMvc;
        this.objectMapper=new ObjectMapper();
        this.bookService=bookService;
    }

    @Test
    public void testThatBookReturnsHttpResponseStatusAs200() throws Exception {
        BookEntity book=TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+book.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatBookSuccessfullyCreatedAndSaved() throws Exception {
        BookDto book=TestDataUtil.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+book.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()));
    }

    @Test
    public void testThatGetAllBooksRetrunsHttpResponseStatusAs200() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk()) ;
    }

//    @Test
//    public void testThatGetAllBookReturnsListOfBookSuccessfully() throws Exception {
//        BookEntity books=TestDataUtil.createTestBookA(null);
//        bookService.createUpdateBook(books.getIsbn(),books);
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/books").contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(books.getIsbn()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(books.getTitle()));
//    }

    @Test
    public void testThatFindBookByIdReturnsHttpResponse200() throws Exception {
        BookEntity books=TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(books.getIsbn(),books);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/books/"+books.getIsbn()).contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFindBookByIdReturns400() throws Exception {
        BookEntity books=TestDataUtil.createTestBookA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+books.getIsbn()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFindBookByIdSuccessfullyReturnsTheBook() throws Exception {
        BookEntity books=TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(books.getIsbn(),books);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+books.getIsbn()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title for Book A"));

    }

    @Test
    public void testThatBookSuccessfullyUpatedAndSaved() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookA(null);
        BookEntity updateBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookDto book=TestDataUtil.createBookDtoA(null);
        book.setIsbn(bookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/books/"+bookEntity.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson)
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatBookSuccessfullyCreateUpdateAndSaved() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookA(null);
        BookEntity updateBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookDto book=TestDataUtil.createBookDtoA(null);
        book.setIsbn(bookEntity.getIsbn());
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/123-432").contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookA(null);
        BookEntity updateBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookDto book=TestDataUtil.createBookDtoA(null);
        book.setIsbn(bookEntity.getIsbn());
        book.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/1234")
                        .contentType(MediaType.APPLICATION_JSON).content(bookJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(1234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED"));
    }

    @Test
    public void testThatBookPartialUpdateReturnsStatus200() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookB(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);
        BookDto bookDto=TestDataUtil.createBookDtoA(null);
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setTitle(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+bookDto.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatBookPartialUpdateReturnsStatus400() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookB(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);
        BookDto bookDto=TestDataUtil.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+bookDto.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatBookPartialUpdateReturnsUpdatedBookSucessfully() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookB(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);
        BookDto bookDto=TestDataUtil.createBookDtoA(null);
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+bookDto.getIsbn()).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("UPDATED"));
    }

    @Test
    public void testThatBookDeletedSuccessfullyAndReturnsHttpStatusCode204() throws Exception {
        BookEntity bookEntity=TestDataUtil.createTestBookB(null);
        bookService.createUpdateBook(bookEntity.getIsbn(),bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/"+bookEntity.getIsbn()).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

