package com.tongji.backend.repository;

import com.tongji.backend.entity.Admin;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("select a from Admin a where a.userID =?1")
    Admin findByUserID(Integer userID);

    @Query("select exists (select  a from Admin a where a.userID=?1 and a.gymID=?2)")
    boolean existsAdminByUserID(@NonNull Integer userID,Integer adminID);
}
