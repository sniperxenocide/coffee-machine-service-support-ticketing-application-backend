package com.cgd.cvm_technical_support.repository.master;

import com.cgd.cvm_technical_support.model.master.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReShop extends JpaRepository<Shop, Long> {
    Optional<Shop> findByShopCode(String shopCode);

    @Query(value = "select s from Shop s where s.responsibleOfficer.id=?1 " +
            " and concat(s.shopCode,s.shopName,s.address) " +
            " like concat('%',?2,'%') ")
    List<Shop> findAllShopsForMso(Long msoId,String search);

    @Query(value = "select s from Shop s where s.id=?1")
    List<Shop> findShopForCustomer(Long shopId);

    @Query(value = "select id from shop where id>?1 ",nativeQuery = true)
    List<Long> getNewShopIds(Long maxId);
}