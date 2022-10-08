package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.CgdCvmTechnicalSupport;
import com.cgd.cvm_technical_support.repository.primary.ReReport;
import com.cgd.cvm_technical_support.dto.Response;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

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

    public Response machineWiseRootCauseCountReport(int page,String sortBy, String sortDir,
           String machineNumber,String machineModel,String machineBrand,
           String division,String region,String territory,String rootCause,
           String startDate, String endDate,Long countStart,Long countEnd){
        LocalDateTime std = LocalDateTime.parse(startDate+"T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate+"T00:00:00").plus(1, ChronoUnit.DAYS);
        int pageSize=10;
        int recordCount = reReport.machineWiseRootCauseCountReport
                (machineNumber,machineModel,machineBrand,division,region,territory,rootCause,
                        std,end,countStart,countEnd,null).size();
        int totalPages = recordCount%pageSize ==0 ? recordCount/pageSize : recordCount/pageSize+1 ;
        int pageIndex = page<1 || page>totalPages ? 1 : page ;
        Pageable pageable = PageRequest.of( pageIndex-1 , pageSize);

        HashMap<String, Object> data = new HashMap<>();
        data.put("dataList",reReport.machineWiseRootCauseCountReport
                (machineNumber,machineModel,machineBrand,division,region,territory,rootCause,
                        std,end,countStart,countEnd,pageable));
        data.put("pageIndex",pageIndex);
        data.put("totalPages",totalPages);
        data.put("pageSize",pageSize);
        data.put("recordCount",recordCount);

        return new Response(true,"Success",data);
    }

    public Response machineWiseTicketCountReport(int page,String sortBy, String sortDir,
                String machineNumber,String machineModel,String machineBrand,
                String division,String region,String territory,String startDate,String endDate,
                                                 Long countStart,Long countEnd){
        LocalDateTime std = LocalDateTime.parse(startDate+"T00:00:00");
        LocalDateTime end = LocalDateTime.parse(endDate+"T00:00:00").plus(1, ChronoUnit.DAYS);
        int pageSize=10;
        int recordCount = reReport.machineWiseTicketCountReport
                (machineNumber,machineModel,machineBrand,division,region,territory,
                        std,end,countStart,countEnd,null).size();
        int totalPages = recordCount%pageSize ==0 ? recordCount/pageSize : recordCount/pageSize+1 ;
        int pageIndex = page<1 || page>totalPages ? 1 : page ;
        Pageable pageable = PageRequest.of( pageIndex-1 , pageSize);

        HashMap<String, Object> data = new HashMap<>();
        data.put("dataList",reReport.machineWiseTicketCountReport
                (machineNumber,machineModel,machineBrand,division,region,territory,std,end,countStart,countEnd,pageable));
        data.put("pageIndex",pageIndex);
        data.put("totalPages",totalPages);
        data.put("pageSize",pageSize);
        data.put("recordCount",recordCount);

        return new Response(true,"Success",data);
    }
}
