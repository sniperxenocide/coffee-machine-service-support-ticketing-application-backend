package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.IssueHeader;
import com.cgd.cvm_technical_support.tmp.TicketCountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReReport extends JpaRepository<IssueHeader,Long> {

    @Query(value = "select new com.cgd.cvm_technical_support.tmp.TicketCountReport( " +
            "i.division,i.region,i.territory,i.currentMsoName,i.currentMsoPhone,i.machineBrand,i.machineModel, " +
            "t.name,s.tagDescription,s.name,count(i.id) " +
            ") " +
            "from IssueHeader i left join Status s on i.currentStatus.id=s.id left join IssueType t on i.issueType.id=t.id " +
            "where i.creationTime between ?1 and ?2 " +
            "group by i.division,i.region,i.territory,i.currentMsoPhone,i.currentMsoName,i.machineBrand,i.machineModel, " +
            "t.name,s.tagDescription,s.name ")
    List<TicketCountReport> ticketCountReport(LocalDateTime startDate, LocalDateTime endDate);

}
