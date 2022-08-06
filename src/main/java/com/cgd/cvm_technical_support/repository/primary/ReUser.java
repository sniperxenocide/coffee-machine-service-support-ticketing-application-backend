package com.cgd.cvm_technical_support.repository.primary;

import com.cgd.cvm_technical_support.model.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReUser extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query(value = "select u.*,r.*,p.* from user u " +
            "inner join user_role ur on u.id = ur.user_id " +
            "inner join role r on ur.role_id = r.id " +
            "inner join role_privilege rp on r.id = rp.role_id " +
            "inner join privilege p on rp.privilege_id = p.id " +
            "where u.id = ?1 and p.id = ?2 ",nativeQuery = true)
    Optional<User> getUserByPrivilege(Long userId, Long privilegeId);

    @Query(value ="select max(remote_id) from user where remote_table=?1 ",nativeQuery = true)
    Long getMaxRemoteId(String remoteTable);

}
