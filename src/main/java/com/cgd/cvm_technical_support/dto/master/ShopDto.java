package com.cgd.cvm_technical_support.dto.master;

import com.cgd.cvm_technical_support.model.master.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data @NoArgsConstructor @AllArgsConstructor
public class ShopDto {
    private Long id;
    private String name;
    private String shopCode;
    private String address;
    private List<MachineDto> machines;

    public ShopDto(Shop shop){
        id= shop.getId();
        name=shop.getShopName();
        shopCode=shop.getShopCode();
        address=shop.getAddress();
        machines=shop.getContracts().stream().map(contract -> new MachineDto(contract.getMachine())).collect(Collectors.toList());
    }
}
