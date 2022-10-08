package com.cgd.cvm_technical_support.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class TicketCountReport {
    private String division;
    private String region;
    private String territory;
    private String msoName;
    private String msoPhone;
    private String machineBrand;
    private String machineModel;
    private String ticketType;
    private String statusGroup;
    private String status;
    private String rootCause;
    private Long ticketCount;
}
