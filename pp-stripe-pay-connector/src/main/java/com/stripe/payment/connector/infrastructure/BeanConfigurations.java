package com.stripe.payment.connector.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfigurations {

    /*
     * Adding this so that GSON message converter (See CustomMessgeConverter class) used for rabbitMQ JSON/Object
     * conversion doesn't clash with the REST level Jackson JSON/object conversion.
     */
    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        ObjectMapper objectMapper = new ObjectMapper();
        // Adding the jackson-datatype-jsr310:2.6.1 for java 8 date/time support.
        objectMapper.registerModule(new JavaTimeModule());
        converter.setObjectMapper(objectMapper);

        return converter;
    }

}
