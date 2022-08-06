package com.cgd.cvm_technical_support.model.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "machine_item")
public class MachineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "oracle_item_code")
    private String oracleItemCode;

    @JsonIgnore @ManyToOne
    @JoinColumn(name = "item_group_id")
    private ItemGroup itemGroup;

    @JsonIgnore @ManyToOne
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;

    @JsonIgnore @ManyToOne
    @JoinColumn(name = "machine_model_id")
    private MachineModel machineModel;

    @JsonIgnore @ManyToOne
    @JoinColumn(name = "uom_id")
    private Uom uom;

    @Transient private String group;
    public String getGroup(){return itemGroup.getName();}
    @Transient private String category;
    public String getCategory(){return itemCategory.getName();}
    @Transient private String model;
    public String getModel(){return machineModel.getName();}
    @Transient private String brand;
    public String getBrand(){return machineModel.getBrandName();}
    @Transient private String itemUom;
    public String getItemUom(){return uom.getName();}
}