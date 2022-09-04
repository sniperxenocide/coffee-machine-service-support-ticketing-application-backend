package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.CgdCvmTechnicalSupport;
import com.cgd.cvm_technical_support.repository.primary.ReIssueHeader;
import com.cgd.cvm_technical_support.repository.primary.ReReport;
import com.cgd.cvm_technical_support.tmp.Response;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class SeReport {
    private final ReReport reReport;
    private final Logger logger = CgdCvmTechnicalSupport.LOGGER;

    public SeReport(ReReport reReport) {
        this.reReport = reReport;
    }

    public Response getTicketCountReport(String startDate,String endDate){
        LocalDateTime std = LocalDateTime.parse(startDate+"T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate+"T00:00:00").plus(1, ChronoUnit.DAYS);
        return new Response(true,"Success",reReport.ticketCountReport(std,end));
    }
}
