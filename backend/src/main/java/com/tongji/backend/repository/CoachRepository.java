package com.tongji.backend.repository;

import com.tongji.backend.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Integer> {

    Optional<Coach> findByUserID(int userID);
}
