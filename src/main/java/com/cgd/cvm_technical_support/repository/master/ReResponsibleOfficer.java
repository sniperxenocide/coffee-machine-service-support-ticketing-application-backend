package com.cgd.cvm_technical_support.repository.master;

import com.cgd.cvm_technical_support.model.master.ResponsibleOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReResponsibleOfficer extends JpaRepository<ResponsibleOfficer, Long> {

    @Query(value = "select id from responsible_officer where id>?1 ",nativeQuery = true)
    List<Long> getNewMsoIds(Long maxId);
}