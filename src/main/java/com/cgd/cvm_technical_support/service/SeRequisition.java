package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.dto.requisition.RequisitionDto;
import com.cgd.cvm_technical_support.dto.requisition.RequisitionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service @RequiredArgsConstructor @Slf4j
public class SeRequisition {
    private final WebClient client;

    public String createRequisition(RequisitionRequest request) throws Exception{
        log.info("Creating Requisition {}",request);
        request.setRaiserWhId(1L);request.setReceiverWhId(2L);
        request.setRequisitionTypeId(1L);request.setCreatedByIdEx(1L);
        RequisitionDto requisitionDto = client.post()
                .uri("/inventory-module/api/requisition/create")
                .body(BodyInserters.fromValue(request)).retrieve()
                .onStatus(HttpStatus::isError, e->Mono.error(new Exception(e.toString())))
                .bodyToMono(RequisitionDto.class).block();

        log.info("Requisition Created {}",requisitionDto);
        return "Requisition Created Successfully";
    }
}
