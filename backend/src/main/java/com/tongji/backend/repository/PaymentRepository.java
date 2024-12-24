package com.tongji.backend.repository;

import com.tongji.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("select p from Payment p join Refund r on p.paymentId=r.paymentID where r.gymID=?1 and r.status=0")
    List<Payment> getAllRefunds(Integer gymID);

}
