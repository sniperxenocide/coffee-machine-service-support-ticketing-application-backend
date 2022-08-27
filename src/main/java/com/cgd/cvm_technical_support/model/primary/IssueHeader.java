package com.cgd.cvm_technical_support.model.primary;

import com.cgd.cvm_technical_support.model.master.Machine;
import com.cgd.cvm_technical_support.model.master.ResponsibleOfficer;
import com.cgd.cvm_technical_support.model.master.Shop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Entity @Table(name = "issue_header")
public class IssueHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "request_token", nullable = false, unique = true)
    private String requestToken;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_user_id", nullable = false)
    private User shopUser;

    @Column(name = "shop_id", nullable = false)
    private Long shopId;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "shop_owner_name", nullable = false)
    private String shopOwnerName;

    @Column(name = "shop_owner_phone", nullable = false, length = 45)
    private String shopOwnerPhone;

    @Column(name = "shop_address")
    private String shopAddress;

    @Column(name = "division",length = 500)
    private String division;

    @Column(name = "region",length = 500)
    private String region;

    @Column(name = "territory",length = 500)
    private String territory;

    @Column(name = "distributor_name",length = 1000)
    private String distributorName;

    @Column(name = "distributor_oracle_code",length = 50)
    private String distributorOracleCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mso_user_id", nullable = false)
    private User msoUser;

    @Column(name = "mso_name")
    private String msoName;

    @Column(name = "mso_phone", nullable = false, length = 45)
    private String msoPhone;

    @Column(name = "mso_location", nullable = false)
    private String msoLocation;

    @Column(name = "current_mso_name")
    private String currentMsoName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "current_mso_user_id", nullable = false)
    private User currentMsoUser;

    @Column(name = "current_mso_phone", nullable = false, length = 45)
    private String currentMsoPhone;

    @Column(name = "current_mso_location", nullable = false)
    private String currentMsoLocation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "current_status_id", nullable = false)
    private Status currentStatus;

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime creationTime;

    @UpdateTimestamp
    @Column(name = "last_update_time", nullable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdateTime;

    @ToString.Exclude @JsonIgnore
    @OneToMany(mappedBy = "issueHeader", orphanRemoval = true)
    @OrderBy("id asc")
    private List<StatusTrack> statusTracks;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "machine_number")
    private String machineNumber;

    @Column(name = "machine_model")
    private String machineModel;

    @Column(name = "machine_brand", length = 100)
    private String machineBrand;

    @Column(name = "response_time")
    private LocalDateTime responseTime;

    @Column(name = "resolution_time")
    private LocalDateTime resolutionTime;

    @Column(name = "closing_time")
    private LocalDateTime closingTime;

    @Column(name = "creation_to_response_time_min")
    private Long creationToResponseTimeMin;

    @Column(name = "response_to_resolution_time_min")
    private Long responseToResolutionTimeMin;

    @Column(name = "resolution_to_closing_time_min")
    private Long resolutionToClosingTimeMin;

    @Column(name = "creation_to_resolution_time_min")
    private Long creationToResolutionTimeMin;

    @Column(name = "creation_to_closing_time_min")
    private Long creationToClosingTimeMin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "issue_type_id",nullable = false)
    private IssueType issueType;

    public IssueHeader(User shopUser, User msoUser, Shop shop, Machine machine,
                       ResponsibleOfficer mso, Status status,IssueType issueType){
        this.shopUser = shopUser;
        this.shopId = shop.getId();
        this.shopName = shop.getShopName();
        this.shopOwnerName = shop.getProprietorName();
        this.shopOwnerPhone = shop.getProprietorPhone();
        this.shopAddress = shop.getAddress();
        this.division = shop.getDivision();
        this.region = shop.getRegion();
        this.territory = shop.getTerritory();
        this.distributorName = shop.getDistributorName();
        this.distributorOracleCode = shop.getDistributorOracleCode();

        this.msoUser = msoUser;
        this.msoName = mso.getName();
        this.msoPhone = mso.getPhone();
        this.msoLocation = msoUser.getLocation();
        this.currentMsoUser = msoUser;
        this.currentMsoName = mso.getName();
        this.currentMsoPhone = mso.getPhone();
        this.currentMsoLocation = msoUser.getLocation();

        this.machineId = machine.getId();
        this.machineNumber = machine.getMachineNumber();
        this.machineModel = machine.getModelNumber();
        this.machineBrand = machine.getMachineBrand().getName();

        this.currentStatus = status;
        this.issueType = issueType;
    }

}