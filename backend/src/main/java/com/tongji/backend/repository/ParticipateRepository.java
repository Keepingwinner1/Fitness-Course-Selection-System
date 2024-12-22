package com.tongji.backend.repository;

import com.tongji.backend.entity.Participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Integer> {
    Participate findByClassId(Integer classId);
    // 查找特定班级和学员的参与记录
    Participate findByClassIdAndTraineeId(Integer classId, Integer traineeId);
}

