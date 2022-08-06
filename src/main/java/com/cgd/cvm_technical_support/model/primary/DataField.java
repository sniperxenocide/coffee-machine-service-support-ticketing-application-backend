package com.cgd.cvm_technical_support.model.primary;

import com.cgd.cvm_technical_support.enums.DataFieldType;
import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name = "data_field")
public class DataField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false,
            columnDefinition = " enum('problem_statement','root_cause','solution','string','date','spare_parts') default 'string' ")
    private DataFieldType type;

    @Column(name = "field_order", nullable = false, columnDefinition = "number default 1 not null")
    private Integer fieldOrder;

}