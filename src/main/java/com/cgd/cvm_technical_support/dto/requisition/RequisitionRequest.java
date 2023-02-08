package com.cgd.cvm_technical_support.dto.requisition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequisitionRequest {
    //@NotNull @Min(1)
    private Long raiserWhId;
    //@NotNull @Min(1)
    private Long receiverWhId;
    //@NotNull @Min(1)
    private Long requisitionTypeId;
    private String receiptNumber;
    private String requisitionDate;
    //@NotNull @Min(1)
    private Long createdByIdEx;
    @NotNull @NotEmpty @Valid
    private List<RequisitionLineRequest> requisitionLines;

}
