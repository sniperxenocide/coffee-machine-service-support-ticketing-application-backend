package com.cgd.cvm_technical_support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableScheduling
@SpringBootApplication
public class CgdCvmTechnicalSupport {

    public static final String MIN_LONG_INF = "-9223372036854775808";
    public static final String MAX_LONG_INF = "9223372036854775807";
    public static final Logger LOGGER = LoggerFactory.getLogger(CgdCvmTechnicalSupport.class);

    public static void main(String[] args) {
        SpringApplication.run(CgdCvmTechnicalSupport.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
