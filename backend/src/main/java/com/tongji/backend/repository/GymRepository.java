package com.tongji.backend.repository;

import com.tongji.backend.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GymRepository extends JpaRepository<Gym, Integer> {

    @Query("select ccc.gymID from (select t from (select c from CourseClass c join Book b on b.classId=c.classId where b.bookId=?1) as cc" +
            " join Teaches t on cc.classId=t.classID) as tt" +
            " join Coach ccc on tt.coachID=ccc.coachID")
    Integer findByBookID(Integer bookID);
}
