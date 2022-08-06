package com.cgd.cvm_technical_support.tmp;

import com.cgd.cvm_technical_support.model.primary.StatusWiseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class IssueHistory {
    private int sequence;
    private String status;
    private String msoPhone;
    private LocalDateTime creationTime;
    private List<StatusWiseData> statusData;
}
