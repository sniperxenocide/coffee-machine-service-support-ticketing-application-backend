package com.cgd.cvm_technical_support.model.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter @RequiredArgsConstructor
@NoArgsConstructor @AllArgsConstructor @ToString
@Table(name = "status_wise_data")
public class StatusWiseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore @ToString.Exclude
    @ManyToOne @NonNull
    @JoinColumn(name = "status_track_id")
    private StatusTrack statusTrack;

    @Column(name = "field_data_id")
    private Long fieldDataId;

    @NonNull
    @Column(name = "field_data", nullable = false, length = 5000)
    private String fieldData;

    @Column(name = "field_data2",  length = 500)
    private String fieldData2;

    @Column(name = "field_data3",  length = 500)
    private String fieldData3;

    @NonNull
    @Column(name = "field_id")
    private Long fieldId;

    @NonNull
    @Column(name = "field_name", nullable = false, length = 500)
    private String fieldName;



}