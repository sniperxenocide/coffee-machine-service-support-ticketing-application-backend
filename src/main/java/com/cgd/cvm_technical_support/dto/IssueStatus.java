package com.cgd.cvm_technical_support.dto;

import com.cgd.cvm_technical_support.model.primary.IssueHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data @AllArgsConstructor @NoArgsConstructor
public class IssueStatus {
    //Used in Mobile App
    private Long id;
    private String issueToken;
    private String issueType;
    private String shopName;
    private String shopMisCode;
    private String shopAddress;
    private String machineNumber;
    private String machineModel;
    private String machineBrand;
    private String status;
    private LocalDateTime issueDate;
    //Used in Mobile App

    private String currentMsoName;
    private String currentMsoPhone;

    private LocalDateTime responseTime;
    private LocalDateTime resolutionTime;
    private LocalDateTime closingTime;

    private Long creationToResponseTimeMin;
    private Long responseToResolutionTimeMin;
    private Long resolutionToClosingTimeMin;
    private Long creationToResolutionTimeMin;
    private Long creationToClosingTimeMin;

    public IssueStatus(IssueHeader i){
        this.id = i.getId();
        this.issueToken = i.getRequestToken();
        this.issueType = i.getIssueType().getName();
        this.shopName = i.getShopName();
        this.shopMisCode = i.getShopUser().getUsername();
        this.shopAddress = i.getShopAddress();
        this.machineNumber = i.getMachineNumber();
        this.machineModel = i.getMachineModel();
        this.machineBrand = i.getMachineBrand();
        this.currentMsoName = i.getCurrentMsoName();
        this.currentMsoPhone = i.getCurrentMsoPhone();
        this.issueDate = i.getCreationTime();
        this.status = i.getCurrentStatus().getName();

        this.responseTime = i.getResponseTime();
        this.resolutionTime = i.getResolutionTime();
        this.closingTime = i.getClosingTime();

        this.creationToResponseTimeMin = getTimeDiff(i.getCreationTime(),i.getResponseTime());
        this.responseToResolutionTimeMin = getTimeDiff(i.getResponseTime(),i.getResolutionTime());
        this.resolutionToClosingTimeMin = getTimeDiff(i.getResolutionTime(),i.getClosingTime());
        this.creationToResolutionTimeMin = getTimeDiff(i.getCreationTime(),i.getResolutionTime());
        this.creationToClosingTimeMin = getTimeDiff(i.getCreationTime(),i.getClosingTime());
    }

    private Long getTimeDiff(LocalDateTime start,LocalDateTime end){
        LocalDateTime s=start,e=end;
        if(s==null) return null;
        if(e==null) e=LocalDateTime.now();
        return s.until(e, ChronoUnit.MINUTES);
    }
}
