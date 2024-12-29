package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "participate")
public class Participate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id; // 主键，唯一标识（推荐添加以支持多对多关系的扩展）

    @Column(name = "trainee_id")
    private Integer traineeId;

    @Column(name = "class_id")
    private Integer classId;

    @Getter
    @Setter
    @Column(name = "grade")
    private Float grade;

    @Lob
    @Column(name = "evaluate")
    private String evaluate;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }


    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}

