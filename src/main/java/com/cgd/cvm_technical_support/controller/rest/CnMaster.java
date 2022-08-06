package com.cgd.cvm_technical_support.controller.rest;

import com.cgd.cvm_technical_support.service.SeMaster;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CnMaster {
    private final SeMaster service;

    public CnMaster(SeMaster service) {
        this.service = service;
    }

    @GetMapping("/api/v1/shop/machines")
    public ResponseEntity<Object> getMachineListForShop(
            HttpServletRequest request, @RequestParam(required = false) String shopCode){
        return ResponseEntity.ok(service.getMachineListForShop(request,shopCode));
    }

    @GetMapping("/api/v1/user/shops")
    public ResponseEntity<Object> getShopList(HttpServletRequest request){
        return ResponseEntity.ok(service.getShops(request));
    }

    @GetMapping("/api/v1/master/data-options")
    public ResponseEntity<Object> getDataFieldOptions(){
        return ResponseEntity.ok(service.getDataFieldOptions());
    }

}
