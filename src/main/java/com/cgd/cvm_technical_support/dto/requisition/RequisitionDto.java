package com.cgd.cvm_technical_support.dto.requisition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequisitionDto {
    private Long id;
    private String code;
    private Long raiserWarehouseId;
    private String raiserWarehouseName;
    private Long receiverWarehouseId;
    private String receiverWarehouseName;
    private String requisitionStatus;
    private String requisitionType;
    private String receiptNumber;
    private String requisitionDate;
    private Long createdByIdEx;
    private LocalDateTime creationTime;
    private List<RequisitionLineDto> requisitionLines;
}
