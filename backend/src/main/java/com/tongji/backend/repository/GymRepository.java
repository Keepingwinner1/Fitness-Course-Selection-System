package com.tongji.backend.repository;

import com.tongji.backend.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GymRepository extends JpaRepository<Gym, Integer> {

    @Query("select ccc.gymID from  CourseClass c join Book b on b.classId=c.classId " +
            " join Teaches t on c.classId=t.classID " +
            " join Coach ccc on t.coachID=ccc.coachID where b.bookId=?1")
    Integer findByBookID(Integer bookID);
}
