package com.example.demo.config;

import com.example.demo.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

//    @Bean
//    public ModelMapper modelMapper(){
//        ModelMapper modelMapper=new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//        return modelMapper;
//    }


    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper;
    }

}
