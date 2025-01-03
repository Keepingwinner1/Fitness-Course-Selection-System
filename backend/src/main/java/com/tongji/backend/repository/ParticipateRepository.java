package com.tongji.backend.repository;

import com.tongji.backend.entity.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Integer> {
    Participate findByClassId(Integer classId);
    // 查找特定班级和学员的参与记录
    @Query("SELECT p FROM Participate p WHERE p.classId = ?1 AND p.traineeId = ?2")
    Participate findByClassIdAndTraineeId(Integer classId, Integer traineeId);

    void deleteParticipateByClassIdAndTraineeId(Integer classId, Integer traineeId);

    @Query("select p from Participate p where p.classId=?1")
    List<Participate> findParticipatesByClassId(Integer classID);

    void deleteByClassIdAndTraineeId(Integer classId, Integer traineeId);
}

