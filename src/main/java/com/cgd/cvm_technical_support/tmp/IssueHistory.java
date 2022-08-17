package com.cgd.cvm_technical_support.tmp;

import com.cgd.cvm_technical_support.model.primary.StatusWiseData;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @RequiredArgsConstructor
public class IssueHistory {
    @NonNull private int sequence;    //Used in Mobile App
    @NonNull private String status;    //Used in Mobile App
    @NonNull private String msoName;
    @NonNull private String msoPhone;    //Used in Mobile App
    @NonNull private LocalDateTime creationTime;    //Used in Mobile App
    @NonNull private List<StatusWiseData> statusData;    //Used in Mobile App

    @NonNull private String createdByUsername;
    private String createdByName;
    private String createdByPhone;
    private String createdByType;
}
