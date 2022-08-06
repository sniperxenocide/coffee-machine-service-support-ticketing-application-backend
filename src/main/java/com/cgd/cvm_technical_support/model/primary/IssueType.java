package com.cgd.cvm_technical_support.model.primary;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "issue_type")
public class IssueType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

}