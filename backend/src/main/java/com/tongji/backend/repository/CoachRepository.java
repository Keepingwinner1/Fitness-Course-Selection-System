package com.tongji.backend.repository;

import com.tongji.backend.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Integer> {

    @Query("select  c from Coach  c where c.userID=?1")
    Coach findByUserID(int userID);

    List<Coach> findAllByStatusAndGymID(int status, int gymID);

    @Query("select exists (select c from Coach  c where c.userID=?1 and c.gymID=?2)")
    boolean existsCoachByCoachID(Integer coachID, Integer gymID);

    @Query("select c from Coach  c where c.coachID=?1")
    Coach findByCoachID(Integer coachID);

    @Query("select exists (select c from Coach c where c.status=1 and c.coachID=?1)")
    boolean existsCoachByStatus(Integer coachID);
}
