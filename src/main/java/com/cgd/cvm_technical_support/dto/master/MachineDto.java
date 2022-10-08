package com.cgd.cvm_technical_support.dto.master;

import com.cgd.cvm_technical_support.model.master.Machine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineDto {
    private Long id;
    private String machineNumber;
    private String model;
    private String brand;

    public MachineDto(Machine machine){
        this.id= machine.getId();
        this.machineNumber = machine.getMachineNumber();
        this.model=machine.getModelNumber();
        this.brand=machine.getMachineBrand().getName();
    }

}
