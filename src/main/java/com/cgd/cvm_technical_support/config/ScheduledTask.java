package com.cgd.cvm_technical_support.config;

import com.cgd.cvm_technical_support.CgdCvmTechnicalSupport;
import com.cgd.cvm_technical_support.model.master.ResponsibleOfficer;
import com.cgd.cvm_technical_support.model.master.Shop;
import com.cgd.cvm_technical_support.model.primary.Role;
import com.cgd.cvm_technical_support.model.primary.User;
import com.cgd.cvm_technical_support.repository.master.ReResponsibleOfficer;
import com.cgd.cvm_technical_support.repository.master.ReShop;
import com.cgd.cvm_technical_support.repository.primary.RePrivilege;
import com.cgd.cvm_technical_support.repository.primary.ReRole;
import com.cgd.cvm_technical_support.repository.primary.ReUser;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ScheduledTask {
    private final Logger logger = CgdCvmTechnicalSupport.LOGGER;
    private final ReUser reUser;
    private final ReShop reShop;
    private final ReResponsibleOfficer reResponsibleOfficer;
    private final ReRole reRole;

    public ScheduledTask(ReUser reUser, ReShop reShop, ReResponsibleOfficer reResponsibleOfficer, ReRole reRole) {
        this.reUser = reUser;
        this.reShop = reShop;
        this.reResponsibleOfficer = reResponsibleOfficer;
        this.reRole = reRole;
    }

    // This Scheduler will Run Every One Hour.
    // This Function will fetch new Shop & MSO from
    // Master Database & insert here as new User
    @Scheduled(fixedDelay = 3600*1000)
    public void syncShopMsoData() {
        try {
            addNewShopUsers();
            addNewMsoUsers();
        } catch (Exception e){logger.info(e.toString());}
    }

    private void addNewShopUsers(){
        Role customerRole = reRole.findById((long)3).orElse(null);
        Long maxShopId = reUser.getMaxRemoteId("shop");
        if(maxShopId==null) maxShopId=(long)0;
        List<Long> shopIds = reShop.getNewShopIds(maxShopId);
        logger.info("Found "+shopIds.size()+" New Shop.Adding As User...");
        for(Long id:shopIds){
            try {
                Shop shop = reShop.findById(id).orElse(null);
                if(shop==null) continue;
                User user = new User(shop.getShopCode(),"12345", shop.getId(), "shop", shop.getAddress());
                user.setRoles(Collections.singletonList(customerRole));
                reUser.save(user);
                logger.info("User Added: "+user);
            }catch (Exception e){logger.error(e.getMessage());}
        }
    }

    private void addNewMsoUsers(){
        Role msoRole = reRole.findById((long)2).orElse(null);
        Long maxMsoId = reUser.getMaxRemoteId("responsible_officer");
        if(maxMsoId==null) maxMsoId=(long)0;
        List<Long> msoIds = reResponsibleOfficer.getNewMsoIds(maxMsoId);
        logger.info("Found "+msoIds.size()+" New MSO.Adding As User...");
        for(Long id:msoIds){
            try {
                ResponsibleOfficer mso = reResponsibleOfficer.findById(id).orElse(null);
                if(mso==null) continue;
                User user = new User(mso.getPhone(), "12345",
                        mso.getId(), "responsible_officer",
                        mso.getDivision()+"-"+mso.getRegion()+"-"+mso.getTerritory());
                user.setRoles(Collections.singletonList(msoRole));
                reUser.save(user);
                logger.info("User Added: "+user);
            }catch (Exception e){logger.error(e.getMessage());}
        }
    }
}
