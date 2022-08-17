package com.cgd.cvm_technical_support.tmp;

import com.cgd.cvm_technical_support.enums.StatusTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StatusTagGroup {
    private StatusTag statusTag;
    private String tagDescription;
}
