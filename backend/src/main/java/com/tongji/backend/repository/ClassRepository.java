package com.tongji.backend.repository;

import com.tongji.backend.entity.Course;
import com.tongji.backend.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassRepository extends JpaRepository<CourseClass, Integer> {

    @Query("SELECT c FROM CourseClass c " +
            "JOIN Participate p ON c.classId = p.classId " +
            "WHERE p.traineeId = :userID AND c.courseEndTime > :now")
    List<CourseClass> findOngoingParticipatedByUser(@Param("userID") Integer userID, @Param("now") LocalDateTime now);

    @Query("SELECT c FROM CourseClass c JOIN Book b ON c.classId = b.classId WHERE b.traineeId = ?1 AND b.bookStatus = 0")
    List<CourseClass> findBookedByUser(Integer userID);

    @Query("SELECT c FROM CourseClass c JOIN Book b ON c.classId = b.classId WHERE b.traineeId = ?1 AND b.bookStatus = 1")
    List<CourseClass> findPaidByUser(Integer userID);

    @Query("SELECT c FROM CourseClass c JOIN Book b ON c.classId = b.classId WHERE b.traineeId = ?1 AND b.bookStatus = 2")
    List<CourseClass> findCanceledByUser(Integer userID);

    @Query("SELECT c FROM CourseClass c " +
            "JOIN Participate p ON c.classId = p.classId " +
            "WHERE p.traineeId = :userID AND c.courseEndTime < :now")
    List<CourseClass> findCompletedClassesByUser(@Param("userID") Integer userID, @Param("now") LocalDateTime now);

}
