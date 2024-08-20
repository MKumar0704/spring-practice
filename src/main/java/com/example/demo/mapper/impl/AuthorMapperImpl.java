package com.example.demo.mapper.impl;

import com.example.demo.domain.dto.AuthorDto;
import com.example.demo.domain.entity.AuthorEntity;
import com.example.demo.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity,AuthorDto> {

    ModelMapper modelMapper;
    AuthorMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto,AuthorEntity.class);
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity,AuthorDto.class);
    }
}
