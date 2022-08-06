package com.cgd.cvm_technical_support.model.primary;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "app_config")
public class AppConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "config_key", unique = true,nullable = false)
    private String configKey;

    @Column(name = "config_value", nullable = false, length = 1000)
    private String configValue;

    @Column(name = "description", length = 1000)
    private String description;

}