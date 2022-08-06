package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.MachineItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReMachineItem extends JpaRepository<MachineItem, Long>, JpaSpecificationExecutor<MachineItem> {

    @Query(value = "select * from machine_item where item_group_id = ?1 ",nativeQuery = true)
    List<MachineItem> getAllByGroupId(Long groupId);
}