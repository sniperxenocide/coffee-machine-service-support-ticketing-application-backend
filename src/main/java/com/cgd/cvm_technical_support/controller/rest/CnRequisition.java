package com.cgd.cvm_technical_support.controller.rest;

import com.cgd.cvm_technical_support.dto.requisition.RequisitionRequest;
import com.cgd.cvm_technical_support.service.SeRequisition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController @RequiredArgsConstructor
public class CnRequisition {
    private final SeRequisition service;

    @PostMapping("/api/v1/requisition/create")
    public ResponseEntity<String> createRequisition(@RequestBody @Valid RequisitionRequest request) throws Exception{
        return ResponseEntity.ok(service.createRequisition(request));
    }

}
