package com.cgd.cvm_technical_support.tmp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class IssueStatus {
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
}
