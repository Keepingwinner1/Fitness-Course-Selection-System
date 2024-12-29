package com.tongji.backend.repository;

import com.tongji.backend.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Integer> {

    Optional<Refund> findByPaymentID(Integer processId);

    @Query("select  exists (select r from Refund  r where  r.userID=?1 and r.bookID =?2 and r.status=0)")
    boolean existsRefundByUserIDAndBookID(Integer userID, Integer bookID);
}
