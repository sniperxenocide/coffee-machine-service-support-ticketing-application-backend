package com.cgd.cvm_technical_support.controller.rest;

import com.cgd.cvm_technical_support.CgdCvmTechnicalSupport;
import com.cgd.cvm_technical_support.service.SeReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CnReport {
    private final String minLongInf = CgdCvmTechnicalSupport.MIN_LONG_INF;
    private final String maxLongInf = CgdCvmTechnicalSupport.MAX_LONG_INF;
    private final SeReport seReport;

    public CnReport(SeReport seReport) {
        this.seReport = seReport;
    }

    @GetMapping("/api/v1/issue/report/ticket_count")
    public ResponseEntity<Object> ticketCountReport(
            @RequestParam(required = false,defaultValue = "1970-01-01") String startDate,
            @RequestParam(required = false,defaultValue = "2100-12-31") String endDate
    ){return  ResponseEntity.ok(seReport.getTicketCountReport(startDate, endDate));}

    @GetMapping("/api/v1/issue/report/machine_wise_root_cause_count")
    public ResponseEntity<Object> machineWiseRootCauseCountReport(
            @RequestParam(required = false,defaultValue = "1") int page,
            @RequestParam(required = false,defaultValue = "") String sortBy,
            @RequestParam(required = false,defaultValue = "") String sortDir,
            @RequestParam(required = false,defaultValue = "%") String machineNumber,
            @RequestParam(required = false,defaultValue = "%") String machineModel,
            @RequestParam(required = false,defaultValue = "%") String machineBrand,
            @RequestParam(required = false,defaultValue = "%") String division,
            @RequestParam(required = false,defaultValue = "%") String region,
            @RequestParam(required = false,defaultValue = "%") String territory,
            @RequestParam(required = false,defaultValue = "%") String rootCause,
            @RequestParam(required = false,defaultValue = "1970-01-01") String startDate,
            @RequestParam(required = false,defaultValue = "2100-12-31") String endDate,
            @RequestParam(required = false,defaultValue = minLongInf ) Long countStart,
            @RequestParam(required = false,defaultValue = maxLongInf) Long countEnd
    ){return ResponseEntity.ok(seReport.machineWiseRootCauseCountReport(page,sortBy,sortDir,
            machineNumber,machineModel,machineBrand,division,region,territory,rootCause,startDate,endDate,
            countStart,countEnd));}

    @GetMapping("/api/v1/issue/report/machine_wise_ticket_count")
    public ResponseEntity<Object> machineWiseTicketCountReport(
            @RequestParam(required = false,defaultValue = "1") int page,
            @RequestParam(required = false,defaultValue = "") String sortBy,
            @RequestParam(required = false,defaultValue = "") String sortDir,
            @RequestParam(required = false,defaultValue = "%") String machineNumber,
            @RequestParam(required = false,defaultValue = "%") String machineModel,
            @RequestParam(required = false,defaultValue = "%") String machineBrand,
            @RequestParam(required = false,defaultValue = "%") String division,
            @RequestParam(required = false,defaultValue = "%") String region,
            @RequestParam(required = false,defaultValue = "%") String territory,
            @RequestParam(required = false,defaultValue = "1970-01-01") String startDate,
            @RequestParam(required = false,defaultValue = "2100-12-31") String endDate,
            @RequestParam(required = false,defaultValue = minLongInf ) Long countStart,
            @RequestParam(required = false,defaultValue = maxLongInf) Long countEnd
    ){return ResponseEntity.ok(seReport.machineWiseTicketCountReport(page,sortBy,sortDir,
            machineNumber,machineModel,machineBrand,division,region,territory,startDate,endDate,
            countStart,countEnd));}
}
