package com.tongji.backend.repository;

import com.tongji.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.userID =?1")
    Admin findByUserID(Integer userID);
}
