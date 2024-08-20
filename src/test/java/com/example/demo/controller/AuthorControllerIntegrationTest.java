package com.example.demo.controller;

import com.example.demo.TestDataUtil;
import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc,AuthorService authorService){
        this.mockMvc=mockMvc;
        this.authorService=authorService;
        this.objectMapper=new ObjectMapper();
    }

    @Test
    public void testThatAuthorSuccessfullyCreatedAndReturnsHttp200Status() throws Exception {
        AuthorEntity author= TestDataUtil.createTestAuthorA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors").contentType(MediaType.APPLICATION_JSON).content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatAuthorSuccessfullyReturnsAndSavedAuthor() throws Exception {
        AuthorEntity author=TestDataUtil.createTestAuthorA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").contentType(MediaType.APPLICATION_JSON).content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("baymax")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("34")
        );
    }

    @Test
    public void testThatAuthorListRetreivedAndGetsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatAuthorListRetrievedSuccessfully() throws Exception {
        AuthorEntity author=TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("baymax")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value("34")
        );
    }

    @Test
    public void testThatGetAuthorReturnsStatus200WhenAuthorExists() throws Exception {
        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAuthorReturnsStatus400WhenAuthorNotExists() throws Exception {
        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetAuthorReturnsWhenAuthroExists() throws Exception {
        AuthorEntity author=TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("baymax")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value("34")
        );
    }

    @Test
    public void testThatIfAuthorExistsAndUpdateReturnsHttpStatus200() throws Exception {

        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        AuthorDto authorDto=TestDataUtil.createAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatIfAuthorExistsAndFullUpdateReturnsHttpStatus400() throws Exception {

        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        AuthorDto authorDto=TestDataUtil.createAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/87")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatIfAuthorExistsAndFullUpdateReturnsSuccessfully() throws Exception {

        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        AuthorDto authorDto=TestDataUtil.createAuthorDtoB();
        authorDto.setId(savedAuthor.getId());
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));
    }

    @Test
    public void testThatIfAuthorPartialUpdateReturnsStauts200() throws Exception {

        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        AuthorDto authorDto=TestDataUtil.createAuthorDtoB();
        authorDto.setId(savedAuthor.getId());
        authorDto.setAge(null);
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/authors/"+savedAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)
                ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatIfAuthorPartialUpdateReturnsUpdatedAuthor() throws Exception {

        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        AuthorDto authorDto=TestDataUtil.createAuthorDtoB();
        authorDto.setId(savedAuthor.getId());
        authorDto.setAge(null);
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()));
    }

    @Test
    public void testThatAuthorDeletedSuccessfullyAndReturnsHttpStatus204() throws Exception {
        AuthorEntity authorEntity=TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);
      mockMvc.perform(
              MockMvcRequestBuilders.delete("/authors/1").contentType(MediaType.APPLICATION_JSON)
      ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

