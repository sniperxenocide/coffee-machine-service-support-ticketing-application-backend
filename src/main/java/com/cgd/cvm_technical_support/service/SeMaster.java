package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.model.master.Contract;
import com.cgd.cvm_technical_support.model.master.Machine;
import com.cgd.cvm_technical_support.model.master.Shop;
import com.cgd.cvm_technical_support.model.primary.Role;
import com.cgd.cvm_technical_support.model.primary.User;
import com.cgd.cvm_technical_support.repository.master.ReShop;
import com.cgd.cvm_technical_support.repository.primary.*;
import com.cgd.cvm_technical_support.tmp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
                            Objects.equals(shop.getResponsibleOfficer().getId(), user.getRemoteId())){
                        new Response(true,"Success",getMachinesByShop(shop));
                    }
                    return new Response(false,"MSO not Assigned for this Shop");
                }
            }
        }
        return new Response(false,"Not Found");
    }

    public Object getMachinesByShop(Shop shop){
        ArrayList<HashMap<String, Object>> machines = new ArrayList<>();
        for (Contract c : shop.getContracts()) {
            Machine m = c.getMachine();
            HashMap<String, Object> o = new HashMap<>();
            o.put("id", m.getId());
            o.put("machineNumber", m.getMachineNumber());
            o.put("model",m.getModelNumber());
            o.put("brand", m.getMachineBrand().getName());
            machines.add(o);
        }
        return machines;
    }

    public Response getShops(HttpServletRequest request,String search){
        try {
            User user = seCommon.getUser(request);
            if(user==null) throw  new Exception("Unauthorized User");
            ArrayList<HashMap<String, Object>> shops = new ArrayList<>();
            ArrayList<Shop> shopFromDb = new ArrayList<>();
            if(seCommon.checkUserRole(user,"Customer")){
                shopFromDb = (ArrayList<Shop>) reShop.findShopForCustomer(user.getRemoteId());
            }
            else if(seCommon.checkUserRole(user,"MSO")){
                search = search.replace(" ","%");
                shopFromDb = (ArrayList<Shop>) reShop.findAllShopsForMso(user.getRemoteId(),search);
            }
            for(Shop s:shopFromDb){
                HashMap<String, Object> o = new HashMap<>();
                o.put("id",s.getId());o.put("shopCode",s.getShopCode());
                o.put("name",s.getShopName());o.put("address",s.getAddress());
                o.put("machines",getMachinesByShop(s));
                shops.add(o);
            }
            log.info("Returning Data: {}",shops);
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
