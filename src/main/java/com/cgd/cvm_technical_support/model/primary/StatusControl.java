package com.cgd.cvm_technical_support.model.primary;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
@Table(name = "status_control")
public class StatusControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "current_status_id", nullable = false)
    private Status currentStatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "next_status_id", nullable = false)
    private Status nextStatus;

}