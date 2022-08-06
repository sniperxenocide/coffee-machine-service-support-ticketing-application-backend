package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReStatus extends JpaRepository<Status, Long> {

    @Query(value = "select s.* from user_role ur inner join role_status rs on ur.role_id = rs.role_id " +
            "inner join status_control sc on rs.status_id=sc.next_status_id " +
            "inner join status s on s.id=sc.next_status_id " +
            "where ur.user_id=?2 and sc.current_status_id=?1 ",
    nativeQuery = true)
    List<Status> getNextStatusForCurrentStatusAndUser(Long currentStatusId,Long userId);

    @Query(value = "select sc.nextStatus from StatusControl sc " +
            "where sc.currentStatus.id=?1 and sc.nextStatus.id=?2 ")
    Optional<Status> getStatusByCurrentAndNext(Long currentStatusId, Long nextStatusId);

    @Query(value = "select s from Status s where " +
            "s.statusTag=com.cgd.cvm_technical_support.enums.StatusTag.START ")
    Optional<Status> getStartStatus();

    @Query(value = "select s from Status s where " +
            "s.statusTag=com.cgd.cvm_technical_support.enums.StatusTag.END ")
    Optional<Status> getEndStatus();

}