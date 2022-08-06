package com.cgd.cvm_technical_support.model.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @JsonIgnore @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "machine_id")
    private Machine machine;

}
