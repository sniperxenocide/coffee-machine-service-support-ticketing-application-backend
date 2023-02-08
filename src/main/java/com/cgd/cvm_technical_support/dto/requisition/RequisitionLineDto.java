package com.cgd.cvm_technical_support.dto.requisition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequisitionLineDto {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemSku;
    private Double itemSkuQty;
    private String requisitionSku;
    private Double requisitionSkuQty;
}
