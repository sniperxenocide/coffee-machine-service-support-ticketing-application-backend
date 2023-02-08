package com.cgd.cvm_technical_support.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController @RequiredArgsConstructor @Slf4j
public class CnItem {
    private final WebClient client;

    @GetMapping("/api/v1/item/get/all")
    public Mono<Object> getAll(@RequestParam(required = false) MultiValueMap<String,String> params) throws Exception{
        String api = "/inventory-module/api/item/get/filter";
        return client.get().uri(builder -> builder.path(api).queryParams(params).build())
                .retrieve ().bodyToMono(Object.class);
    }
}
