package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RePrivilege extends JpaRepository<Privilege,Long> {
    Optional<Privilege> findByName(String name);
    Optional<Privilege> findByApi(String api);

}
