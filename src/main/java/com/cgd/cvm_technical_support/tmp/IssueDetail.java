package com.cgd.cvm_technical_support.tmp;

import com.cgd.cvm_technical_support.model.primary.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class IssueDetail {
    @NonNull private Long id;    // Being Used in Mobile App
    @NonNull private String issueToken;    // Being Used in Mobile App
    @NonNull private String issueType;    // Being Used in Mobile App
    @NonNull private String shopName;    // Being Used in Mobile App
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
}
