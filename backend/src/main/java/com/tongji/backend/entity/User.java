package com.tongji.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false, unique = true)
    private Integer userID;

    @Column(name = "userName", length = 50, nullable = false)
    private String userName;

    @Column(name = "Password", length = 50, nullable = false)
    private String password;

    @Column(name = "Email", length = 100, nullable = false)
    private String email;

    @Column(name = "registrationTime", nullable = false)
    private LocalDateTime registrationTime;

    @Column(name = "Age", length = 3)
    private Integer age;

    @Column(name = "Gender", length = 10)
    private String gender;

    @Column(name = "isMember", nullable = false)
    private Boolean isMember;

    @Column(name = "isDelete", nullable = false)
    private Boolean isDelete;

    @Column(name = "iconURL", length = 100)
    private String iconURL;

    @Lob
    @Column(name = "Tags")
    private String tags;

    @Column(name = "Introduction", length = 100)
    private String introduction;

    @Column(name = "Salt", length = 255, nullable = false)
    private String salt;

    @Column(name = "goalType", length = 20)
    private String goalType;

    @Column(name = "Duration")
    private LocalTime duration;

    @Column(name = "goalWeight", precision = 2)
    private Float goalWeight;

    // Getters and Setters
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Float getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(Float goalWeight) {
        this.goalWeight = goalWeight;
    }
}
