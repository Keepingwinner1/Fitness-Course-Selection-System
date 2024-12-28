package com.tongji.backend.repository;


import com.tongji.backend.entity.Coursepublish;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursePublishRepository extends JpaRepository<Coursepublish,Integer> {

}
