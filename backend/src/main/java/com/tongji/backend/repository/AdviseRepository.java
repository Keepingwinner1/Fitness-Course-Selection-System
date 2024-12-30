package com.tongji.backend.repository;

import com.tongji.backend.entity.Advise;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdviseRepository  extends JpaRepository<Advise, Integer> {
    Advise findByClassId(Integer classID);

    Advise findByClassIdAndUserId(Integer classID, Integer traineeId);

    void deleteAdviseByClassIdAndUserId(Integer classId, @NonNull Integer userID);

    void deleteByClassIdAndUserId(Integer classId, Integer traineeId);
}
