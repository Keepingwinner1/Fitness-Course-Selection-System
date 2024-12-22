package com.tongji.backend.entity.dto;

import java.time.LocalDateTime;

public class BookDTO {

    private Integer classId; // 课程班级 ID
    private Integer traineeId; // 学员 ID

    // Getters and Setters

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }
}

