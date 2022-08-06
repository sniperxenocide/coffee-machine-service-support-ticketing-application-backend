package com.cgd.cvm_technical_support.model.master;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Table(name = "shop")
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "shop_code")
    private String shopCode;
    @Column(name = "proprietor_name")
    private String proprietorName;
    @Column(name = "proprietor_phone")
    private String proprietorPhone;
    private String address;
    private String division;
    private String region;
    private String territory;


    @OneToMany(mappedBy = "shop", orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Contract> contracts ;

    @ManyToOne
    @JoinColumn(name = "responsible_officer_id")
    private ResponsibleOfficer responsibleOfficer;

}