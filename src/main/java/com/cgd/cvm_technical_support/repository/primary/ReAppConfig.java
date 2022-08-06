package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReAppConfig extends JpaRepository<AppConfig, Long> {
    Optional<AppConfig> findByConfigKey(String configKey);
}