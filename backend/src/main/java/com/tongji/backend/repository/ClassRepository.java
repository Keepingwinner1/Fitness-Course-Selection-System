package com.tongji.backend.repository;

import com.tongji.backend.entity.Advise;
import com.tongji.backend.entity.Course;
import com.tongji.backend.entity.CourseClass;
import com.tongji.backend.entity.dto.ClassDTO;
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

    // 根据 coachID 和 courseID 获取匹配的 Class 对象
    @Query("SELECT c FROM CourseClass c " +
            "JOIN Teaches t ON c.classId = t.classID " +
            "WHERE t.coachID = :coachID AND c.courseId = :courseID")
    List<CourseClass> findByCoachAndCourse(@Param("coachID") Integer coachID, @Param("courseID") Integer courseID);


    // 获取用户预订的课程以及对应的 bookId 和 course 的所有属性
    @Query("SELECT new com.tongji.backend.entity.dto.ClassDTO(" +
            "c.classId, co.courseType, co.courseName, co.courseDescription, " +
            "co.courseGrade, co.coursePhotoUrl, c.capacity, c.coursePrice, " +
            "c.courseStartTime, c.courseEndTime, c.dayOfWeek, b.bookId) " +
            "FROM CourseClass c " +
            "JOIN Book b ON c.classId = b.classId " +
            "JOIN Course co ON c.courseId = co.courseId " +
            "WHERE b.traineeId = :userID AND b.bookStatus = 0")
    List<ClassDTO> findBookedCoursesByUser(@Param("userID") Integer userID);

}

