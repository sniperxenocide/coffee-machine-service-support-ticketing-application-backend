package com.cgd.cvm_technical_support.model.master;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machine_number")
    private String machineNumber;
    @Column(name = "machine_code")
    private String machineCode;

    @Column(name = "model_number",length = 100)
    private String modelNumber;

    @OneToOne(mappedBy = "machine", orphanRemoval = true)
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private MachineBrand machineBrand;

}
