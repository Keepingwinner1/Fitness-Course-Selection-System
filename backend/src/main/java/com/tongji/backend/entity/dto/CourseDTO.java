package com.tongji.backend.entity.dto;

import jakarta.persistence.Column;

public class CourseDTO {

    private Integer courseId;

    private String courseType;

    private String courseName;

    private String courseDescription;

    private Integer courseGrade;

    private String coursePhotoUrl;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getCourseGrade() {
        return courseGrade;
    }

    public void setCourseGrade(Integer courseGrade) {
        this.courseGrade = courseGrade;
    }

    public String getCoursePhotoUrl() {
        return coursePhotoUrl;
    }

    public void setCoursePhotoUrl(String coursePhotoUrl) {
        this.coursePhotoUrl = coursePhotoUrl;
    }
}
