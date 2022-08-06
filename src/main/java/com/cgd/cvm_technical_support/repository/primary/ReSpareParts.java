package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.SpareParts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReSpareParts extends JpaRepository<SpareParts, Long> {
}