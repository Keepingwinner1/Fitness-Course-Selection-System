package com.tongji.backend.repository;

import com.tongji.backend.entity.Teaches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeachesRepository extends JpaRepository<Teaches, Integer> {

    @Query("select exists(t) from Teaches t where  t.classID=?1")
    boolean existsTeachesByClassID(Integer classID);

    void deleteByClassID(Integer processId);
}
