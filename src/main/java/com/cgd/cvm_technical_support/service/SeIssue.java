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
import com.cgd.cvm_technical_support.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service @Slf4j
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
    private final Logger logger = CgdCvmTechnicalSupport.LOGGER;

    private final ArrayList<String> sortDirections = new ArrayList<>(Arrays.asList("asc","desc"));
    private final ArrayList<String> sortByColumns = new ArrayList<>(Arrays.asList("currentStatus.statusOrder","lastUpdateTime","creationToResolutionTimeMin","creationToClosingTimeMin"));

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
    public Response getAllIssues(HttpServletRequest request,int page,String sortBy,
                                 String sortDir, String shopCode,String machineNumber,
                                 String msoPhone,String ticketNumber,String statusId,
                                 String statusTag,String startDate,String endDate,
                                 Long creationToResolveMinStart,Long creationToResolveMinEnd,
                                 String creationToResolveWithNull,
                                 Long creationToClosingMinStart,Long creationToClosingMinEnd,
                                 String creationToClosingWithNull){
        try {
            logger.info("Get All Issue Method Start");
            LocalDateTime startD = LocalDateTime.parse(startDate+"T00:00:00");
            LocalDateTime endD = LocalDateTime.parse(endDate+"T00:00:00").plus(1,ChronoUnit.DAYS);
            int pageSize=10;
            int recordCount = reIssueHeader.getAllByFilter(shopCode,machineNumber,msoPhone,
                    ticketNumber,statusId,statusTag,startD,endD,
                    creationToResolveMinStart,creationToResolveMinEnd,creationToResolveWithNull,
                    creationToClosingMinStart,creationToClosingMinEnd,creationToClosingWithNull,null).size();
            int totalPages = recordCount%pageSize ==0 ? recordCount/pageSize : recordCount/pageSize+1 ;
            int pageIndex = page<1 || page>totalPages ? 1 : page ;

            logger.info("Get All Issue Record Count:"+recordCount);

            ArrayList<Sort.Order> orders = new ArrayList();
            if(sortBy.length()==0 && sortDir.length()==0){
                orders.add(new Sort.Order(Sort.Direction.ASC, sortByColumns.get(0)));
                orders.add(new Sort.Order(Sort.Direction.ASC, sortByColumns.get(1)));
            }
            else {
                orders.add(new Sort.Order(Sort.Direction.DESC, sortByColumns.get(2)));
            }

            Pageable pageable = PageRequest.of( pageIndex-1 , pageSize,
                    Sort.by(orders));
//            Sort.Direction.fromString(sortDirections.get(0)), sortByColumns.get(0),sortByColumns.get(1)
            try {
                User user = seCommon.getUser(request);
                if(user==null) throw new Exception("Unauthorized User");
                ArrayList<IssueStatus> issueStatusList = (ArrayList<IssueStatus>) reIssueHeader.getAllByFilter(shopCode,machineNumber,msoPhone,ticketNumber,
                        statusId,statusTag,startD,endD,creationToResolveMinStart,creationToResolveMinEnd,creationToResolveWithNull,
                        creationToClosingMinStart,creationToClosingMinEnd,creationToClosingWithNull,pageable);
                logger.info("Get All Issue Record Fetch Query Complete");
                HashMap<String, Object> data = new HashMap<>();
                data.put("tickets",issueStatusList);
                data.put("pageIndex",pageIndex);
                data.put("totalPages",totalPages);
                data.put("pageSize",pageSize);
                data.put("recordCount",recordCount);
                data.put("statusList",reStatus.getStatusWiseTicketSummary(statusTag));
                data.put("statusTagList",reStatus.getStatusTagWiseTicketSummary());
                logger.info("Get All Issue Loop Complete & Method End");
                return new Response(true,"Success",data);
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }

    }

    public Response getWebDashboardAnalytics(){
        HashMap<String, Object> data = new HashMap<>();
        data.put("statusList",reStatus.getStatusWiseTicketSummary("%"));
        data.put("statusTagList",reStatus.getStatusTagWiseTicketSummary());
        return new Response(true,"Success",data);
    }

    public Response createNewIssue(HttpServletRequest request,String shopCode,Long machineId,Long issueTypeId){
        try {
            log.info("Creating new Ticket...");
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
            log.info("Ticket Created Successfully. Token: {}",reqToken);
            return new Response(true,"Issue Created Successfully.Token: "+reqToken);
        }catch (Exception e){
            logger.info(e.getMessage());
            return new Response(false,e.getMessage());
        }
    }

    //Only in Mobile App
    public Response getAllActiveIssueStatus(HttpServletRequest request){
        User user = seCommon.getUser(request);
        ArrayList<Long> allowedIssueTypes = new ArrayList<>();
        for(Role r: user.getRoles())
            for (IssueType it:r.getIssueTypes())
                allowedIssueTypes.add(it.getId());

        ArrayList<IssueStatus> activeIssues;
        if(seCommon.checkUserRole(user,"Customer")){
            activeIssues = (ArrayList<IssueStatus>) reIssueHeader
                    .getActiveIssuesForShopUser(user.getId(),allowedIssueTypes);
        }
        else if(seCommon.checkUserRole(user,"MSO")){
            activeIssues = (ArrayList<IssueStatus>) reIssueHeader
                    .getActiveIssuesForMsoUser(user.getId(),allowedIssueTypes);
        }
        else return new Response(false,"Unauthorized");
        return new Response(true,"Success",activeIssues);
    }
    //Only in Mobile App

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
        List<Status> nextStatus = reStatus.getNextStatusForCurrentStatusAndUser(
                issueHeader.getCurrentStatus().getId(), user.getId());
        List<IssueHistory> issueHistoryList = new ArrayList<>();
        int seq=1;
        for(StatusTrack track:issueHeader.getStatusTracks()){
            try {
                ResponsibleOfficer statusTrackMso = reResponsibleOfficer.findById(track.getMsoUser().getRemoteId()).orElse(null);
                if(statusTrackMso==null) statusTrackMso = new ResponsibleOfficer();
                IssueHistory issueHistory = new IssueHistory(seq++,track.getStatus().getName(), statusTrackMso.getName(),
                        track.getMsoPhone(), track.getCreationTime(), track.getStatusWiseData(),
                        track.getCreatedByUser().getUsername());

                if(track.getCreatedByUser().getRemoteTable()==null || track.getCreatedByUser().getRemoteTable().isEmpty()){
                    issueHistory.setCreatedByType("Monitoring Officer");
                }
                else if(track.getCreatedByUser().getRemoteTable().equalsIgnoreCase("shop")){
                    Shop s = reShop.findById(track.getCreatedByUser().getRemoteId()).orElse(null);
                    if(s==null) s = new Shop();
                    issueHistory.setCreatedByName(s.getShopName());
                    issueHistory.setCreatedByPhone(s.getProprietorPhone());
                    issueHistory.setCreatedByType("Shop Owner");
                }
                else if(track.getCreatedByUser().getRemoteTable().equalsIgnoreCase("responsible_officer")){
                    ResponsibleOfficer m = reResponsibleOfficer.findById(track.getCreatedByUser().getRemoteId()).orElse(null);
                    if(m==null) m = new ResponsibleOfficer();
                    issueHistory.setCreatedByName(m.getName());
                    issueHistory.setCreatedByPhone(m.getPhone());
                    issueHistory.setCreatedByType("MSO");
                }
                issueHistoryList.add(issueHistory);
            }catch (Exception e){e.printStackTrace();}
        }
        return new IssueDetail(issueHeader,nextStatus,issueHistoryList);
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

            //setting response,resolution,closing time
            try {
                if(statusTrack.getIssueHeader().getStatusTracks().size()==2){
                    try {
                        issueHeader.setResponseTime(LocalDateTime.now());
                        issueHeader.setCreationToResponseTimeMin(issueHeader.getCreationTime().until(issueHeader.getResponseTime(),ChronoUnit.MINUTES));
                    }catch (Exception e){e.printStackTrace();}
                }
                if(statusTrack.getStatus().getStatusTag().equals(StatusTag.PRE_END)){
                    issueHeader.setResolutionTime(LocalDateTime.now());
                    try {
                        issueHeader.setResponseToResolutionTimeMin(issueHeader.getResponseTime().until(issueHeader.getResolutionTime(),ChronoUnit.MINUTES));
                        issueHeader.setCreationToResolutionTimeMin(issueHeader.getCreationTime().until(issueHeader.getResolutionTime(),ChronoUnit.MINUTES));
                    }catch (Exception e){e.printStackTrace();}
                }
                if(statusTrack.getStatus().getStatusTag().equals(StatusTag.END)){
                    issueHeader.setClosingTime(LocalDateTime.now());
                    try {
                        issueHeader.setResolutionToClosingTimeMin(issueHeader.getResolutionTime().until(issueHeader.getClosingTime(),ChronoUnit.MINUTES));
                        issueHeader.setCreationToClosingTimeMin(issueHeader.getCreationTime().until(issueHeader.getClosingTime(),ChronoUnit.MINUTES));
                    }catch (Exception e){e.printStackTrace();}
                }
                reIssueHeader.save(issueHeader);
            }catch (Exception e){e.printStackTrace();}
            //setting response,resolution,closing time

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
            issueHeader.setClosingTime(LocalDateTime.now());

            try {
                issueHeader.setCreationToClosingTimeMin(issueHeader.getCreationTime().until(issueHeader.getClosingTime(),ChronoUnit.MINUTES));
            }catch (Exception e){e.printStackTrace();}
            try {
                issueHeader.setResolutionToClosingTimeMin(issueHeader.getResolutionTime().until(issueHeader.getClosingTime(),ChronoUnit.MINUTES));
            }catch (Exception e){e.printStackTrace();}

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
