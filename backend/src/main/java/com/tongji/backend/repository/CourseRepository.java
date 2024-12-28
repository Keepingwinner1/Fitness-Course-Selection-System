package com.tongji.backend.repository;

import com.tongji.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    List<Course> findByCourseType(String type);

    @Query("SELECT c FROM Course c JOIN Book b ON c.courseId = b.classId WHERE b.traineeId = ?1 AND b.bookTime = ?2")
    List<Course> findTodayCoursesByUser(Integer userID, LocalDate today);

    @Query("SELECT c FROM Course c JOIN Participate p ON c.courseId = p.classId WHERE p.traineeId = ?1")
    List<Course> findParticipatedCoursesByUser(Integer userID);

    @Query("SELECT c FROM Course c JOIN Book b ON c.courseId = b.classId WHERE b.traineeId = ?1 AND b.bookStatus = 0 ")
    List<Course> findBookedCoursesByUser(Integer userID);

    @Query("SELECT c FROM Course c JOIN Book b ON c.courseId = b.classId WHERE b.traineeId = ?1 AND b.bookStatus = 2")
    List<Course> findCanceledCoursesByUser(Integer userID);

    @Query("select c from Course c join Coursepublish cc on c.courseId=cc.course where cc.coach=?1")
    List<Course> findByCoachID(Integer coachID);
}
