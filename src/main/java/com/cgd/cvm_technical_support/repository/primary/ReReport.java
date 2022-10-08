package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.IssueHeader;
import com.cgd.cvm_technical_support.dto.MachineWiseRootCauseCountReport;
import com.cgd.cvm_technical_support.dto.MachineWiseTicketCountReport;
import com.cgd.cvm_technical_support.dto.TicketCountReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReReport extends JpaRepository<IssueHeader,Long> {

    @Query(value = "select new com.cgd.cvm_technical_support.dto.TicketCountReport" +
            "(i.division,i.region,i.territory,i.currentMsoName,i.currentMsoPhone,i.machineBrand,i.machineModel, " +
            "t.name,s.tagDescription,s.name,swd.fieldData,count(i.id)) " +
            "from IssueHeader i " +
            "left join Status s on i.currentStatus.id=s.id " +
            "left join IssueType t on i.issueType.id=t.id " +
            "left join StatusTrack st on st.issueHeader.id=i.id and st.status.id in (select ss.id from Status ss where ss.statusTag='PRE_END') " +
            "left join StatusWiseData swd on st.id = swd.statusTrack.id and swd.fieldId=2 " +
            "where i.creationTime between ?1 and ?2 " +
            "group by i.division,i.region,i.territory,i.currentMsoPhone,i.currentMsoName,i.machineBrand,i.machineModel, " +
            "t.name,s.tagDescription,s.name,swd.fieldData ")
    List<TicketCountReport> ticketCountReport(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select new com.cgd.cvm_technical_support.dto.MachineWiseRootCauseCountReport " +
            "(i.division,i.region,i.territory,i.machineBrand,i.machineModel,i.machineNumber,swd.fieldData,count(i.id)) " +
            "from IssueHeader i left join StatusTrack st on i.id = st.issueHeader.id " +
            "left join StatusWiseData swd on st.id = swd.statusTrack.id " +
            "where swd.fieldId=2 and i.creationTime between ?8 and ?9 " +
            "and i.machineNumber like concat('%',?1,'%') and i.machineModel like concat('%',?2,'%') " +
            "and i.machineBrand like concat('%',?3,'%') and i.division like concat('%',?4,'%') " +
            "and i.region like concat('%',?5,'%') and i.territory like concat('%',?6,'%') " +
            "and swd.fieldData like concat('%',?7,'%') "+
            "group by i.division,i.region,i.territory,i.machineBrand,i.machineModel,i.machineNumber,swd.fieldData " +
            "having count(i.id) between ?10 and ?11 order by count(i.id) desc ")
    List<MachineWiseRootCauseCountReport> machineWiseRootCauseCountReport
            (String machineNumber, String machineModel, String machineBrand, String division,
             String region, String territory,String rootCause,LocalDateTime startDate,LocalDateTime endDate,
             Long countStart,Long countEnd,Pageable pageable);

    @Query(value = "select new com.cgd.cvm_technical_support.dto.MachineWiseTicketCountReport " +
            "(i.division,i.region,i.territory,i.machineBrand,i.machineModel,i.machineNumber,count(i.id)) " +
            "from IssueHeader i where i.creationTime between ?7 and ?8 " +
            "and i.machineNumber like concat('%',?1,'%') and i.machineModel like concat('%',?2,'%') " +
            "and i.machineBrand like concat('%',?3,'%') and i.division like concat('%',?4,'%') " +
            "and i.region like concat('%',?5,'%') and i.territory like concat('%',?6,'%') " +
            "group by i.division,i.region,i.territory,i.machineBrand,i.machineModel,i.machineNumber " +
            "having count(i.id) between ?9 and ?10 order by count(i.id) desc ")
    List<MachineWiseTicketCountReport> machineWiseTicketCountReport
            (String machineNumber, String machineModel, String machineBrand, String division,
             String region, String territory, LocalDateTime startDate, LocalDateTime endDate,
             Long countStart,Long countEnd,Pageable pageable);



}
