package com.zendesk.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * The {@link ZendeskClientJavaApplication} is a
 * cloud-native Spring Boot application
 * that manages a bounded context for
 * @{link DealInfo}
 * and @{link Contacts}
 *
 * @author Olga Savin
 */
@SpringBootApplication
public class ZendeskClientJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZendeskClientJavaApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
