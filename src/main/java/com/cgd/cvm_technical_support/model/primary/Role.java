package com.cgd.cvm_technical_support.model.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @ToString
@Entity @Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @JsonIgnore @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "role_privilege",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private List<Privilege> privileges;

    @JsonIgnore @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "role_status",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    private List<Status> statusList;

    @Column(name = "description", nullable = false)
    private String description;


    @JsonIgnore @ToString.Exclude @ManyToMany
    @JoinTable(name = "role_issue_type",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_type_id"))
    private List<IssueType> issueTypes ;

}
