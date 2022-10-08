package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.dto.master.MachineDto;
import com.cgd.cvm_technical_support.dto.master.ShopDto;
import com.cgd.cvm_technical_support.model.master.Contract;
import com.cgd.cvm_technical_support.model.master.Machine;
import com.cgd.cvm_technical_support.model.master.Shop;
import com.cgd.cvm_technical_support.model.primary.Role;
import com.cgd.cvm_technical_support.model.primary.User;
import com.cgd.cvm_technical_support.repository.master.ReShop;
import com.cgd.cvm_technical_support.repository.primary.*;
import com.cgd.cvm_technical_support.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeMaster {
    private final SeCommon seCommon;
    private final ReShop reShop;
    private final ReProblemStatement reProblemStatement;
    private final ReRootCause reRootCause;
    private final ReSolution reSolution;
    private final ReSpareParts reSpareParts;
    private final ReIssueType reIssueType;
    private final ReMachineItem reMachineItem;

    public SeMaster(SeCommon seCommon, ReShop reShop, ReProblemStatement reProblemStatement, ReRootCause reRootCause, ReSolution reSolution, ReSpareParts reSpareParts, ReIssueType reIssueType, ReMachineItem reMachineItem) {
        this.seCommon = seCommon;
        this.reShop = reShop;
        this.reProblemStatement = reProblemStatement;
        this.reRootCause = reRootCause;
        this.reSolution = reSolution;
        this.reSpareParts = reSpareParts;
        this.reIssueType = reIssueType;
        this.reMachineItem = reMachineItem;
    }

    public Response getMachineListForShop(HttpServletRequest request, String shopCode){
        User user = seCommon.getUser(request);
        if(user==null) return new Response(false,"Unauthorized User");
        for(Role role: user.getRoles()) {
            if (role.getName().equalsIgnoreCase("Customer")) {
                Shop shop = reShop.findById(user.getRemoteId()).orElse(null);
                if(shop==null) return new Response(false,"No Shop Found for this User");
                return new Response(true,"Success",getMachinesByShop(shop));
            }
            else if(role.getName().equalsIgnoreCase("MSO")){
                if(shopCode==null || shopCode.trim().length()==0)
                    return new Response(false,"Shop MIS Code Required");
                else {
                    Shop shop = reShop.findByShopCode(shopCode).orElse(null);
                    if(shop==null) return new Response(false,"Invalid Shop Code");
                    if(shop.getResponsibleOfficer().getPhone().equals(user.getUsername()) &&
                            (Objects.equals(shop.getResponsibleOfficer().getId(), user.getRemoteId()))){
                        return new Response(true,"Success",getMachinesByShop(shop));
                    }
                    return new Response(false,"MSO not Assigned for this Shop");
                }
            }
        }
        return new Response(false,"Not Found");
    }

    public Object getMachinesByShop(Shop shop){
        return shop.getContracts().stream()
                .map(contract -> new MachineDto(contract.getMachine()))
                .collect(Collectors.toList());
    }

    public Response getShops(HttpServletRequest request,String search){
        try {
            log.info("Getting Shops from User.");
            User user = seCommon.getUser(request);
            if(user==null) throw  new Exception("Unauthorized User");
            ArrayList<ShopDto> shops = new ArrayList<>();
            if(seCommon.checkUserRole(user,"Customer")){
                shops = (ArrayList<ShopDto>) reShop.findShopForCustomer(user.getRemoteId());
            }
            else if(seCommon.checkUserRole(user,"MSO")){
                search = search.replace(" ","%");
                shops = (ArrayList<ShopDto>) reShop.findAllShopsForMso(user.getRemoteId(),search);
            }
            log.info("Returning {} Shop.",shops.size());
            return new Response(true,"Success",shops);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            return new Response(false,e.getMessage());
        }
    }

    public Response getDataFieldOptions(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("problem_statement",reProblemStatement.findAll());
        map.put("root_cause",reRootCause.findAll());
        map.put("solution",reSolution.findAll());
        map.put("issue_type",reIssueType.findAll());
        map.put("machine_item",reMachineItem.getAllByGroupId((long)1)); //Only Spare Parts
        return new Response(true,"Success",map);
    }

}
