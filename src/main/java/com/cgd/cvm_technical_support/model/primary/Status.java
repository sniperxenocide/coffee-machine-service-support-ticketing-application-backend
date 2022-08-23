package com.cgd.cvm_technical_support.model.primary;

import com.cgd.cvm_technical_support.enums.StatusTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @NoArgsConstructor
@AllArgsConstructor @Getter @Setter @ToString
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description",length = 500)
    private String description;

    @ToString.Exclude
    @ManyToMany
    @OrderBy("fieldOrder asc")
    @JoinTable(name = "status_wise_data_field",
            joinColumns = @JoinColumn(name = "status_id"),
            inverseJoinColumns = @JoinColumn(name = "data_field_id"))
    private List<DataField> dataFields ;

    @Column(name = "default_name", nullable = false, length = 100)
    private String defaultName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_tag", nullable = false,columnDefinition = " enum('START','END','PRE_END','COMMON') default 'COMMON' ")
    private StatusTag statusTag;

    @Column(name = "tag_description",length = 500)
    private String tagDescription;

    @Column(name = "status_order")
    private Long statusOrder;

}