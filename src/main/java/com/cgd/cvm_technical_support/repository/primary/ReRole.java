package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReRole extends JpaRepository<Role,Long> {

    @Query(value = "select rs.status_id from user_role ur inner join role_status rs on ur.role_id=rs.role_id " +
            "where ur.user_id=?1 and rs.status_id=?2 ",
    nativeQuery = true)
    Optional<Long> checkUserRoleStatus(Long userId,Long statusId);

}
