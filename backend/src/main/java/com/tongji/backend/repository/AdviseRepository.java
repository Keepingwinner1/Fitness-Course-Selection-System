package com.tongji.backend.repository;

import com.tongji.backend.entity.Advise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdviseRepository  extends JpaRepository<Advise, Integer> {
    Advise findByClassId(Integer classID);
}
