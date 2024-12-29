package com.tongji.backend.repository;

import com.tongji.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t from Task t where t.classID=?1")
    List<Task> findByClassID(Integer classID);
}
