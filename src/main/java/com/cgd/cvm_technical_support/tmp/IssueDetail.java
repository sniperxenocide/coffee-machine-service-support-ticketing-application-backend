package com.cgd.cvm_technical_support.tmp;

import com.cgd.cvm_technical_support.model.primary.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class IssueDetail {
    private Long id;
    private String issueToken;
    private String issueType;
    private String shopName;
    private String shopPhone;
    private String shopMisCode;
    private String shopAddress;
    private String division;
    private String region;
    private String territory;
    private String machineNumber;
    private String machineModel;
    private String machineBrand;
    private String currentMsoName;
    private String currentMsoPhone;
    private LocalDateTime issueDate;
    private Status currentStatus;
    private List<Status> nextStatusList;
    private List<IssueHistory> issueHistory;
}
