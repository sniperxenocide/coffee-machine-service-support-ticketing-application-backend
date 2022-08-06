package com.cgd.cvm_technical_support.model.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "privilege")
public class Privilege{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "api", nullable = false, unique = true, length = 100)
    private String api;

    @Column(name = "method", nullable = false, length = 25)
    private String method;

    @Column(name = "description", length = 500)
    private String description;

}
