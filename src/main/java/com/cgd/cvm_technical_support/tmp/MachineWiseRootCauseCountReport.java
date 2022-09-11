package com.cgd.cvm_technical_support.tmp;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class MachineWiseRootCauseCountReport {
    private String division;
    private String region;
    private String territory;
    private String machineBrand;
    private String machineModel;
    private String machineNumber;
    private String rootCause;
    private Long count;
}
