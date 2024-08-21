package com.example.demo.controller;


import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.mapper.Mapper;
import com.example.demo.mapper.impl.AuthorMapperImpl;
import com.example.demo.services.AuthorService;
import lombok.extern.java.Log;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class AuthorController {

    private AuthorService authorService;

   private Mapper<AuthorEntity,AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService,Mapper<AuthorEntity,AuthorDto> authorMapper){
        this.authorService=authorService;
        this.authorMapper=authorMapper;
    }

    @PostMapping(path ="/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity author = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(author), HttpStatus.CREATED);

    }

    @RequestMapping("/")
    public String home(){
        return "Dockerize and deploy the Spring Boot application using kubernetes";
    }

    @RequestMapping("/info")
    public String info(){
        return "{----- /authors, /books, /authors/{author_id}, /books/{isbn} -----}";
    }
    
    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors(){
        List<AuthorEntity> authors=authorService.findAll();
        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path ="/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthor=authorService.findOne(id);
        return foundAuthor.map(
                authorEntity -> {
                    AuthorDto authorDto=authorMapper.mapTo(authorEntity);
                    return new ResponseEntity<>(authorDto,HttpStatus.OK);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id,@RequestBody AuthorDto authorDto){

        if(!authorService.isExistsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        AuthorEntity authorEntity=authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    @PatchMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> partialAuthorUpdate(@PathVariable("id") Long id,@RequestBody AuthorDto authorDto){

        if(!authorService.isExistsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity=authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthor=authorService.partialUpdate(id,authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor),HttpStatus.OK);
    }

    @DeleteMapping(path="/authors/{id}")
    public ResponseEntity deleteAuthor(@PathVariable Long id){
        authorService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
