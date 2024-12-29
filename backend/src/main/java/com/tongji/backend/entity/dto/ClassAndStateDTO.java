package com.tongji.backend.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassAndStateDTO {

    private Integer classId;

    private String courseType;

    private String courseName;

    private String courseDescription;

    private Integer courseGrade;

    private String coursePhotoUrl;

    private Integer capacity;

    private Integer coursePrice;

    private LocalDateTime courseStartTime;

    private LocalDateTime courseEndTime;

    private String dayOfWeek;

    private String state;
}
