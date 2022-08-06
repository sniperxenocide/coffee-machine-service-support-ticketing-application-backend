package com.cgd.cvm_technical_support.service;

import com.cgd.cvm_technical_support.CgdCvmTechnicalSupport;
import com.cgd.cvm_technical_support.enums.StatusTag;
import com.cgd.cvm_technical_support.model.master.Contract;
import com.cgd.cvm_technical_support.model.master.Machine;
import com.cgd.cvm_technical_support.model.master.ResponsibleOfficer;
import com.cgd.cvm_technical_support.model.master.Shop;
import com.cgd.cvm_technical_support.model.primary.*;
import com.cgd.cvm_technical_support.repository.master.ReMachine;
import com.cgd.cvm_technical_support.repository.master.ReResponsibleOfficer;
import com.cgd.cvm_technical_support.repository.master.ReShop;
import com.cgd.cvm_technical_support.repository.primary.*;
import com.cgd.cvm_technical_support.tmp.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SeIssue {
    private final SeCommon seCommon;
    private final ReShop reShop;
    private final ReRole reRole;
    private final ReMachine reMachine;
    private final ReStatus reStatus;
    private final ReDataField reDataField;
    private final ReIssueHeader reIssueHeader;
    private final ReIssueType reIssueType;
    private final ReStatusTrack reStatusTrack;
    private final ReStatusWiseData reStatusWiseData;
    private final ReResponsibleOfficer reResponsibleOfficer;
    public final Logger logger = CgdCvmTechnicalSupport.LOGGER;

    public SeIssue(SeCommon seCommon, ReShop reShop, ReRole reRole, ReMachine reMachine, ReStatus reStatus, ReDataField reDataField, ReIssueHeader reIssueHeader, ReIssueType reIssueType, ReStatusTrack reStatusTrack, ReStatusWiseData reStatusWiseData, ReResponsibleOfficer reResponsibleOfficer) {
        this.seCommon = seCommon;
        this.reShop = reShop;
        this.reRole = reRole;
        this.reMachine = reMachine;
        this.reStatus = reStatus;
        this.reDataField = reDataField;
        this.reIssueHeader = reIssueHeader;
        this.reIssueType = reIssueType;
        this.reStatusTrack = reStatusTrack;
        this.reStatusWiseData = reStatusWiseData;
        this.reResponsibleOfficer = reResponsibleOfficer;
    }

    // This is for Monitoring Web Portal
    public Response getAllIssues(HttpServletRequest request){
        try {
            User user = seCommon.getUser(request);
            if(user==null) throw new Exception("Unauthorized User");
            ArrayList<IssueDetail> issueDetailList = new ArrayList<>();
            for (IssueHeader ih:reIssueHeader.findAll())
                issueDetailList.add(getIssueDetailObject(ih,user));
            return new Response(true,"Success",issueDetailList);
        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }
    }

    public Response createNewIssue(HttpServletRequest request,String shopCode,Long machineId,Long issueTypeId){
        try {
            User user = seCommon.getUser(request);
            if(user==null) throw new Exception("Unauthorized Request");
            User shopUser = null,msoUser = null;
            Shop shop = null; Machine machine = null;
            ResponsibleOfficer mso = null;
            for(Role role: user.getRoles()) {
                if (role.getName().equalsIgnoreCase("Customer")) {
                    shopUser = user;
                    shop = reShop.findById(shopUser.getRemoteId()).orElse(null);
                    if(shop==null) throw new Exception("No Shop Assigned for this User");
                    mso = shop.getResponsibleOfficer();
                    if(mso==null) throw new Exception("No MSO Assigned for this Shop");
                    msoUser = seCommon.getUser(mso.getPhone());
                    if(msoUser==null) throw new Exception("No User found for this MSO");
                    break;
                }
                else if (role.getName().equalsIgnoreCase("MSO")) {
                    msoUser = user;
                    if(msoUser.getRemoteId()==null) throw new Exception("MSO Profile not Found");
                    shop = reShop.findByShopCode(shopCode).orElse(null);
                    if(shop==null) throw new Exception("Shop Not Found");
                    mso = shop.getResponsibleOfficer();
                    if(mso==null) throw new Exception("MSO Profile not Found");
                    if(!Objects.equals(mso.getId(), msoUser.getRemoteId()) ||
                            !Objects.equals(mso.getPhone(), msoUser.getUsername()))
                        throw new Exception("Unauthorized MSO");
                    shopUser = seCommon.getUser(shop.getShopCode());
                    if (shopUser==null) throw new Exception("Shop User not Found");
                    break;
                }
            }
            machine = reMachine.findById(machineId).orElse(null);
            if(machine==null) throw new Exception("Machine Not Found");
            boolean machineListed = false;
            assert shop != null : "Shop Profile not Found";
            for(Contract c: shop.getContracts()){
                if (Objects.equals(c.getMachine().getId(), machine.getId())) {
                    machineListed = true;
                    break;
                }
            }
            if(!machineListed) throw new Exception("Machine Not Assigned to this SHop");
            if(reIssueHeader.checkActiveIssueForShop(shop.getId(), machine.getId()).size()>0){
                throw new Exception("An issue Already Exist for this Shop-Machine");
            }

            Status initStatus = reStatus.getStartStatus().orElse(null);
            if(initStatus==null) throw new Exception("Init Status Not Present");

            IssueType issueType = reIssueType.findById(issueTypeId).orElse(null);
            if(issueType==null) throw new Exception("Ticket Type (ID:"+issueTypeId+") not Found");
            IssueHeader issueHeader = new IssueHeader(shopUser,msoUser,shop,machine,mso,initStatus,issueType);
            String reqToken = new SimpleDateFormat("yyMMdd").format(new Date());
            int cnt = reIssueHeader.countByRequestTokenLike(reqToken+"%")+1;
            reqToken = reqToken.concat("-"+cnt);
            issueHeader.setRequestToken(reqToken);
            StatusTrack statusTrack = new StatusTrack(issueHeader,initStatus,msoUser, mso.getPhone(), msoUser.getLocation(),user);
            reIssueHeader.save(issueHeader);
            reStatusTrack.save(statusTrack);
            return new Response(true,"Issue Created Successfully.Token: "+reqToken);
        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }
    }

    public Response getAllActiveIssueStatus(HttpServletRequest request){
        User user = seCommon.getUser(request);
        ArrayList<Long> allowedIssueTypes = new ArrayList<>();
        for(Role r: user.getRoles())
            for (IssueType it:r.getIssueTypes())
                allowedIssueTypes.add(it.getId());

        ArrayList<IssueStatus> activeIssues;
        if(seCommon.checkUserRole(user,"Customer")){
            activeIssues = (ArrayList<IssueStatus>) reIssueHeader
                    .getUserRoleWiseAllActiveIssueStatus(user.getId(),null,allowedIssueTypes);
        }
        else if(seCommon.checkUserRole(user,"MSO")){
            activeIssues = (ArrayList<IssueStatus>) reIssueHeader
                    .getUserRoleWiseAllActiveIssueStatus(null,user.getId(),allowedIssueTypes);
        }
        else return new Response(false,"Unauthorized");
        return new Response(true,"Success",activeIssues);
    }

    public Response getIssueDetail(HttpServletRequest request,Long id){
        try {
            User user = seCommon.getUser(request);
            if(user==null) throw new Exception("Unauthorized User");
            IssueHeader issueHeader = reIssueHeader.findById(id).orElse(null);
            if(issueHeader==null) throw new Exception("Not Found");
            IssueDetail issueDetail = getIssueDetailObject(issueHeader,user);
            return new Response(true,"Success",issueDetail);
        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }
    }

    public IssueDetail getIssueDetailObject(IssueHeader issueHeader,User user){
        Shop shop = reShop.findById(issueHeader.getShopUser().getRemoteId()).orElse(null);
        if(shop==null) shop = new Shop();
        ResponsibleOfficer mso = reResponsibleOfficer.findById(issueHeader.getCurrentMsoUser().getRemoteId()).orElse(null);
        if(mso==null) mso = new ResponsibleOfficer();
        IssueDetail issueDetail = new IssueDetail(
                issueHeader.getId(), issueHeader.getRequestToken(), issueHeader.getIssueType().getName(),
                issueHeader.getShopName(),issueHeader.getShopOwnerPhone(), issueHeader.getShopUser().getUsername(),
                issueHeader.getShopAddress(), shop.getDivision(), shop.getRegion(), shop.getTerritory(),
                issueHeader.getMachineNumber(),issueHeader.getMachineModel(), issueHeader.getMachineBrand(),
                mso.getName(),mso.getPhone(), issueHeader.getCreationTime(),issueHeader.getCurrentStatus(),
                reStatus.getNextStatusForCurrentStatusAndUser(issueHeader.getCurrentStatus().getId(),
                        user.getId()),
                null);
        List<IssueHistory> issueHistory = new ArrayList<>();
        int seq=1;
        for(StatusTrack track:issueHeader.getStatusTracks()){
            issueHistory.add(new IssueHistory(seq++,track.getStatus().getName(),
                    track.getMsoPhone(), track.getCreationTime(), track.getStatusWiseData()));
        }
        issueDetail.setIssueHistory(issueHistory);
        return issueDetail;
    }

    public Response setNextStatus(HttpServletRequest request, IssueNextStatus nextStatus){
        try {
            logger.info(String.valueOf(nextStatus));
            User user = seCommon.getUser(request);
            if(user==null) throw new Exception("Unauthorized Request.");
            IssueHeader issueHeader = reIssueHeader.findById(nextStatus.getIssueId()).orElse(null);
            if(issueHeader==null) throw new Exception("Issue Not found.");

            if (issueHeader.getCurrentStatus().getStatusTag().equals(StatusTag.END))
                throw new Exception("Issue Already Closed");

            Status statusNext = reStatus.getStatusByCurrentAndNext
                    (issueHeader.getCurrentStatus().getId(), nextStatus.getNextStatusId()).orElse(null);
            if(statusNext==null) throw new Exception("Invalid Next Status");

            if(!reRole.checkUserRoleStatus(user.getId(),statusNext.getId()).isPresent())
                throw new Exception("User is not Authorized to set this Status");

            for(int i=0;i<nextStatus.getDataList().size();i++){
                boolean matchFound = false;
                for(DataField df: statusNext.getDataFields()){
                    if(Objects.equals(df.getId(), nextStatus.getFieldId(i))) matchFound=true;
                }
                if(!matchFound) throw new Exception("Insufficient/Invalid Data Fields Provided");

                if(nextStatus.getFieldData(i).trim().length()==0)
                    throw new Exception("Data Can't be Empty");
            }

            if(seCommon.checkUserRole(user,"MSO")){
                issueHeader.setCurrentMsoUser(user);
                issueHeader.setCurrentMsoPhone(user.getUsername());
                issueHeader.setCurrentMsoLocation(user.getLocation());
            }
            issueHeader.setCurrentStatus(statusNext);
            issueHeader = reIssueHeader.save(issueHeader);

            StatusTrack statusTrack = new StatusTrack(issueHeader,issueHeader.getCurrentStatus(),
                    issueHeader.getCurrentMsoUser(),issueHeader.getCurrentMsoPhone(),
                    issueHeader.getCurrentMsoLocation(), user);
            statusTrack = reStatusTrack.save(statusTrack);

            for(int i=0;i<nextStatus.getDataList().size();i++){
                DataField df = reDataField.findById(nextStatus.getFieldId(i)).orElse(null);
                assert df != null;
                StatusWiseData swd = new StatusWiseData(statusTrack, nextStatus.getFieldData(i),df.getId(),df.getName());

                if(nextStatus.getFieldDataId(i)!=null) swd.setFieldDataId(nextStatus.getFieldDataId(i));
                if(nextStatus.getFieldData2(i)!=null) swd.setFieldData2(nextStatus.getFieldData2(i));
                if(nextStatus.getFieldData3(i)!=null)swd.setFieldData3(nextStatus.getFieldData3(i));

                reStatusWiseData.save(swd);
            }

        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }
        return new Response(true,"Success");
    }

    public Response closeIssue(HttpServletRequest request,Long id){
        try {
            User user = seCommon.getUser(request);
            if(user==null) throw new Exception("Unauthorized User");
            IssueHeader issueHeader = reIssueHeader.findById(id).orElse(null);
            if(issueHeader==null) throw new Exception("Issue Not Found");

            Status endStatus = reStatus.getEndStatus().orElse(null);
            if(endStatus==null) throw new Exception("End Status Not Found");
            issueHeader.setCurrentStatus(endStatus);

            StatusTrack statusTrack = new StatusTrack(issueHeader,endStatus,issueHeader.getCurrentMsoUser(),
                    issueHeader.getCurrentMsoPhone() ,issueHeader.getCurrentMsoLocation(), user);

            reIssueHeader.save(issueHeader);
            reStatusTrack.save(statusTrack);
        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }
        return new Response(true,"Success");
    }
}
