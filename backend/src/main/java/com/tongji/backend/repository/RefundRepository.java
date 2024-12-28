package com.tongji.backend.repository;

import com.tongji.backend.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Integer> {

    Optional<Refund> findByPaymentID(Integer processId);
}
