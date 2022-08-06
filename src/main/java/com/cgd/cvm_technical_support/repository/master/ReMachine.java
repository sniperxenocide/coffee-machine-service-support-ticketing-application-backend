package com.cgd.cvm_technical_support.repository.master;

import com.cgd.cvm_technical_support.model.master.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReMachine extends JpaRepository<Machine, Long> {
}