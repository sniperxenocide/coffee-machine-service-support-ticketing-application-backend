package com.cgd.cvm_technical_support.controller.rest;

import com.cgd.cvm_technical_support.service.SeReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CnReport {

    private final SeReport seReport;

    public CnReport(SeReport seReport) {
        this.seReport = seReport;
    }

    @GetMapping("/api/v1/issue/report/ticket_count")
    public ResponseEntity<Object> ticketCountReport(
            @RequestParam(required = false,defaultValue = "1970-01-01") String startDate,
            @RequestParam(required = false,defaultValue = "2100-12-31") String endDate
    ){
        return  ResponseEntity.ok(seReport.getTicketCountReport(startDate, endDate));
    }
}
