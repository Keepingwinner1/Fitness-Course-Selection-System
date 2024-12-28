package com.tongji.backend.repository;

import com.tongji.backend.entity.Advise;
import com.tongji.backend.entity.Course;
import com.tongji.backend.entity.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("select c from Teaches t join CourseClass c on t.classID=c.classId where t.coachID=?1")
    List<CourseClass> findTeachClass(Integer coachID);

    @Query("SELECT cc FROM CourseClass cc " +
            "JOIN Teaches t ON cc.classId = t.classID " +
            "JOIN Coach c ON t.coachID = c.coachID " +
            "WHERE c.gymID = :gymID AND cc.status = 0")
    List<CourseClass> findByGymID(@Param("gymID") Integer gymID);

    @Query("SELECT c FROM CourseClass c JOIN Book b ON c.classId = b.classId WHERE b.classId = :classId AND b.bookStatus = 0")
    List<CourseClass> findByClassId(Integer classId);

    //根据传入的变化量更新capacity
    @Modifying //必须添加这个注解，否则默认为select
    @Query("UPDATE CourseClass c SET c.capacity = c.capacity + :num WHERE c.classId = :classId")
    void updateCapacity(Integer classId, Integer num);
}

