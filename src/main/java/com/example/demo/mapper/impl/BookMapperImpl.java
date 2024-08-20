package com.example.demo.mapper.impl;

import com.example.demo.domain.dto.BookDto;
import com.example.demo.domain.entity.BookEntity;
import com.example.demo.mapper.Mapper;
import com.example.demo.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    ModelMapper modelMapper;

    public BookMapperImpl() {
    this.modelMapper=new ModelMapper();
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto,BookEntity.class);
    }
}
