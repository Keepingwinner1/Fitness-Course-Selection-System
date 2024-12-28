package com.tongji.backend.entity.dto;

import lombok.Data;

@Data
public class NewCourseDTO {

    private String courseType;

    private String courseName;

    private String courseDescription;

    private Integer courseGrade;

    private String coursePhotoUrl;
}
