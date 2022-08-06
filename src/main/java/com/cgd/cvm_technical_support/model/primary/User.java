package com.cgd.cvm_technical_support.model.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
@AllArgsConstructor @RequiredArgsConstructor
@Entity @Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NonNull @JsonIgnore
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @NonNull @Column(name = "remote_id")
    private Long remoteId;

    @NonNull @Column(name = "remote_table", length = 100)
    private String remoteTable;

    @NonNull @Column(name = "location", length = 500)
    private String location;

    @CreationTimestamp @JsonIgnore
    @Column(name = "creation_time",
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

    @JsonIgnore
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

}
