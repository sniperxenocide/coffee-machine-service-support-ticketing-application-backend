package com.cgd.cvm_technical_support.dto.requisition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequisitionLineRequest {
    @NotNull @Min(1) private Long itemId;
    @NotNull @Min(1) private Long requisitionSkuId;
    @NotNull @Min(1) private Double requisitionQty;
}