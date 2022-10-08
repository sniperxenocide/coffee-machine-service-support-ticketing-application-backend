package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.IssueHeader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cgd.cvm_technical_support.dto.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReIssueHeader extends JpaRepository<IssueHeader, Long> {

    @Query(value = "select new com.cgd.cvm_technical_support.dto.IssueStatus(i) " +
            "from IssueHeader i where " +
            "i.shopUser.username like concat('%',?1,'%') and " +
            "i.machineNumber like concat('%',?2,'%') and " +
            "i.msoPhone like concat('%',?3,'%') and " +
            "i.requestToken like concat('%',?4,'%') and " +
            "concat(i.currentStatus.id,'') like ?5 and " +
            "concat(i.currentStatus.statusTag,'') like ?6 and " +
            "i.creationTime between ?7 and ?8 and " +
            " ( " +
            "   (i.creationToResolutionTimeMin between ?9 and ?10) or " +
            "   (i.creationToResolutionTimeMin is null and ?11 = 'true' ) " +
            " ) and " +
            " ( " +
            "   (i.creationToClosingTimeMin between ?12 and ?13) or " +
            "   (i.creationToClosingTimeMin is null and ?14 = 'true') " +
            " ) "
    )
    List<IssueStatus> getAllByFilter(String shopCode,String machineNumber,
                                     String msoPhone,String ticketNumber,String statusId,
                                     String statusTag,LocalDateTime startDate, LocalDateTime endDate,
                                     Long crToRslMinSt,Long crToRslMinEn,String crToRslWithNull,
                                     Long crToClMinSt,Long crToClMinEn,String crToClWithNull,
                                     Pageable pageable);


    int countByRequestTokenLike(String requestToken);

    @Query(value = " select ih " +
            " from IssueHeader ih where " +
            " ih.shopId=?1 and ih.machineId=?2 and " +
            " ih.currentStatus.statusTag in " +
            "(com.cgd.cvm_technical_support.enums.StatusTag.START,com.cgd.cvm_technical_support.enums.StatusTag.COMMON) ")
    List<IssueHeader> checkActiveIssueForShop(Long shopId, Long machineId);

    @Query(
            value = " select new com.cgd.cvm_technical_support.dto.IssueStatus(i) " +
                    " from IssueHeader i where i.shopUser.id=?1 " +
                    " and i.currentStatus.statusTag <> com.cgd.cvm_technical_support.enums.StatusTag.END " +
                    " and i.issueType.id in ?2 "
    )
    List<IssueStatus> getActiveIssuesForShopUser(Long shopUserId,ArrayList<Long> issueTypes);

    @Query(
            value = " select new com.cgd.cvm_technical_support.dto.IssueStatus(i) " +
                    " from IssueHeader i where i.msoUser.id=?1 " +
                    " and concat(i.currentStatus.statusTag,'') not in ('PRE_END','END') " +
                    " and i.issueType.id in ?2 "
    )
    List<IssueStatus> getActiveIssuesForMsoUser(Long msoUserId,ArrayList<Long> issueTypes);


}