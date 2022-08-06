package com.cgd.cvm_technical_support.model.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity @Table(name = "status_track")
public class StatusTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Exclude @JsonIgnore @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "issue_header_id", nullable = false)
    private IssueHeader issueHeader;

    @ManyToOne(optional = false) @NonNull
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne(optional = false) @NonNull
    @JoinColumn(name = "mso_user_id", nullable = false)
    private User msoUser;

    @NonNull
    @Column(name = "mso_phone", nullable = false, length = 45)
    private String msoPhone;

    @NonNull
    @Column(name = "mso_location", nullable = false)
    private String msoLocation;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne(optional = false) @NonNull
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdByUser;

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

    @JsonIgnore @ToString.Exclude
    @OneToMany(mappedBy = "statusTrack", orphanRemoval = true)
    private List<StatusWiseData> statusWiseData;

}