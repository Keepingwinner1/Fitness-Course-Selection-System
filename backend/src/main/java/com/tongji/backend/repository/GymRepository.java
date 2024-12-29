package com.tongji.backend.repository;

import com.tongji.backend.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GymRepository extends JpaRepository<Gym, Integer> {

    @Query("SELECT ccc.gymID " +
            "FROM CourseClass c " +
            "JOIN Teaches t ON c.classId = t.classID " +
            "JOIN Coach ccc ON t.coachID = ccc.coachID " +
            "WHERE c.classId = :bookID")
    Integer findByBookID(@Param("bookID") Integer bookID);
}
