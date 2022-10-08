package com.cgd.cvm_technical_support.dto;

import com.cgd.cvm_technical_support.enums.StatusTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusWiseSummary {
    private Long id;
    private String status;
    private StatusTag statusTag;
    private String tagDescription;
    private long ticketCount;
}
