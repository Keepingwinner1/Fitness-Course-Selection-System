package com.tongji.backend.entity.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class ClassDTO {

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

    private Integer bookId;


    // 无参构造函数
    public ClassDTO() {
    }

    // 有参构造函数
    public ClassDTO(Integer classId, String courseType, String courseName, String courseDescription,
                    Integer courseGrade, String coursePhotoUrl, Integer capacity, Integer coursePrice,
                    LocalDateTime courseStartTime, LocalDateTime courseEndTime, String dayOfWeek, Integer bookId) {
        this.classId = classId;
        this.courseType = courseType;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseGrade = courseGrade;
        this.coursePhotoUrl = coursePhotoUrl;
        this.capacity = capacity;
        this.coursePrice = coursePrice;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
        this.dayOfWeek = dayOfWeek;
        this.bookId = bookId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Integer coursePrice) {
        this.coursePrice = coursePrice;
    }

    public LocalDateTime getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(LocalDateTime courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public LocalDateTime getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(LocalDateTime courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
