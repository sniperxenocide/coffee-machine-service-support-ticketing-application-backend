package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.ProblemStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReProblemStatement extends JpaRepository<ProblemStatement, Long>, JpaSpecificationExecutor<ProblemStatement> {
}