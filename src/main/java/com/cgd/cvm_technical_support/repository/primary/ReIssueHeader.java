package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.IssueHeader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.cgd.cvm_technical_support.tmp.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ReIssueHeader extends JpaRepository<IssueHeader, Long> {

    @Query(value = "select i from IssueHeader i where " +
            "i.shopUser.username like concat('%',?1,'%') and " +
            "i.machineNumber like concat('%',?2,'%') and " +
            "i.msoPhone like concat('%',?3,'%') and " +
            "i.requestToken like concat('%',?4,'%') and " +
            "concat(i.currentStatus.id,'') like ?5 and " +
            "concat(i.currentStatus.statusTag,'') like ?6 " )
    List<IssueHeader> getAllByFilter(String shopCode,String machineNumber,
                                     String msoPhone,String ticketNumber,String statusId,
                                     String statusTag, Pageable pageable);

    @Query(value = "select count(i) from IssueHeader i where " +
            "i.shopUser.username like concat('%',?1,'%') and " +
            "i.machineNumber like concat('%',?2,'%') and " +
            "i.msoPhone like concat('%',?3,'%') and " +
            "i.requestToken like concat('%',?4,'%') and " +
            "concat(i.currentStatus.id,'') like ?5 and " +
            "concat(i.currentStatus.statusTag,'') like ?6 ")
    int countAllByFilter(String shopCode,String machineNumber,
                         String msoPhone,String ticketNumber,String statusId,String statusTag);

    int countByRequestTokenLike(String requestToken);

    @Query(value = " select ih " +
            " from IssueHeader ih where " +
            " ih.shopId=?1 and ih.machineId=?2 and " +
            " ih.currentStatus.statusTag in " +
            "(com.cgd.cvm_technical_support.enums.StatusTag.START,com.cgd.cvm_technical_support.enums.StatusTag.COMMON) ")
    List<IssueHeader> checkActiveIssueForShop(Long shopId, Long machineId);

    @Query(
            value = " select new com.cgd.cvm_technical_support.tmp.IssueStatus " +
            " (i.id,i.requestToken,i.issueType.name,i.shopName,i.shopUser.username,i.shopAddress, " +
            " i.machineNumber,i.machineModel,i.machineBrand,i.currentStatus.name,i.creationTime) " +
            " from IssueHeader i where " +
            " ((?1 is null and ?2 is not null) or (?1 is not null and ?2 is null)) " +
            " and (?1 is null or i.shopUser.id=?1) and (?2 is null or i.msoUser.id=?2) " +
            " and i.currentStatus.statusTag <> com.cgd.cvm_technical_support.enums.StatusTag.END " +
            " and i.issueType.id in ?3 "
    )
    List<IssueStatus> getUserRoleWiseAllActiveIssueStatus(Long shopUserId, Long msoUserId, ArrayList<Long> issueTypes);


}