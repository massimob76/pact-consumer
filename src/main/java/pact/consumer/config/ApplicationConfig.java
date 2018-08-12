package pact.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pact.consumer.exception.ResponseErrorHandlerImpl;

@Configuration
public class ApplicationConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ResponseErrorHandlerImpl(objectMapper));
        return restTemplate;
    }
}
