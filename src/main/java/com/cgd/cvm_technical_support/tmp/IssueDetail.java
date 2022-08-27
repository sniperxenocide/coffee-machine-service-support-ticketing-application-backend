package com.cgd.cvm_technical_support.tmp;

import com.cgd.cvm_technical_support.model.primary.IssueHeader;
import com.cgd.cvm_technical_support.model.primary.Status;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class IssueDetail {
    @NonNull private Long id;    // Being Used in Mobile App
    @NonNull private String issueToken;    // Being Used in Mobile App
    @NonNull private String issueType;    // Being Used in Mobile App
    @NonNull private String shopName;    // Being Used in Mobile App
    @NonNull private String shopOwner;
    @NonNull private String shopPhone;    // Being Used in Mobile App
    @NonNull private String shopMisCode;    // Being Used in Mobile App
    @NonNull private String shopAddress;    // Being Used in Mobile App
    @NonNull private String division;    // Being Used in Mobile App
    @NonNull private String region;    // Being Used in Mobile App
    @NonNull private String territory;    // Being Used in Mobile App
    @NonNull private String machineNumber;    // Being Used in Mobile App
    @NonNull private String machineModel;    // Being Used in Mobile App
    @NonNull private String machineBrand;    // Being Used in Mobile App
    @NonNull private String currentMsoName;    // Being Used in Mobile App
    @NonNull private String currentMsoPhone;    // Being Used in Mobile App
    @NonNull private LocalDateTime issueDate;    // Being Used in Mobile App
    @NonNull private Status currentStatus;    // Being Used in Mobile App
    @NonNull private List<Status> nextStatusList;    // Being Used in Mobile App
    private List<IssueHistory> issueHistory;    // Being Used in Mobile App

    private LocalDateTime responseTime;
    private LocalDateTime resolutionTime;
    private LocalDateTime closingTime;

    private Long creationToResponseTimeMin;
    private Long responseToResolutionTimeMin;
    private Long resolutionToClosingTimeMin;
    private Long creationToResolutionTimeMin;
    private Long creationToClosingTimeMin;

    public IssueDetail(IssueHeader i,List<Status> nextStatus,List<IssueHistory> history){
        this.id = i.getId();
        this.issueToken = i.getRequestToken();
        this.issueType = i.getIssueType().getName();
        this.shopName = i.getShopName();
        this.shopOwner = i.getShopOwnerName();
        this.shopPhone = i.getShopOwnerPhone();
        this.shopMisCode = i.getShopUser().getUsername();
        this.shopAddress = i.getShopAddress();
        this.division = i.getDivision();
        this.region = i.getRegion();
        this.territory = i.getTerritory();
        this.machineNumber = i.getMachineNumber();
        this.machineModel = i.getMachineModel();
        this.machineBrand = i.getMachineBrand();
        this.currentMsoName = i.getCurrentMsoName();
        this.currentMsoPhone = i.getCurrentMsoPhone();
        this.issueDate = i.getCreationTime();
        this.currentStatus = i.getCurrentStatus();
        this.nextStatusList = nextStatus;
        this.issueHistory = history;

        this.responseTime = i.getResponseTime();
        this.resolutionTime = i.getResolutionTime();
        this.closingTime = i.getClosingTime();

        this.creationToResponseTimeMin = getTimeDiff(i.getCreationTime(),i.getResponseTime());
        this.responseToResolutionTimeMin = getTimeDiff(i.getResponseTime(),i.getResolutionTime());
        this.resolutionToClosingTimeMin = getTimeDiff(i.getResolutionTime(),i.getClosingTime());
        this.creationToResolutionTimeMin = getTimeDiff(i.getCreationTime(),i.getResolutionTime());
        this.creationToClosingTimeMin = getTimeDiff(i.getCreationTime(),i.getClosingTime());

    }

    Long getTimeDiff(LocalDateTime start,LocalDateTime end){
        LocalDateTime s=start,e=end;
        if(s==null) return null;
        if(e==null) e=LocalDateTime.now();
        return s.until(e, ChronoUnit.MINUTES);
    }


}
