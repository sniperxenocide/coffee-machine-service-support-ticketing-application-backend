package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.DataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReDataField extends JpaRepository<DataField, Long> {
}