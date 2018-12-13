package com.example.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static com.example.phonebook.web.json.JacksonAutoConfiguration.getMapper;

@SpringBootApplication
@EnableCaching
public class Main {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(getMapper());
        return jsonConverter;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}