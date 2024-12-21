package com.tongji.backend.entity.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserDto {

    @Getter
    private String userName;

    @Getter
    private String password;

    private String email;

    private LocalDateTime registrationTime;

    private Integer age;

    private String gender;


    private Boolean isMember;


    private Boolean isDelete;


    private String iconURL;

    @Getter
    private String tags;

    private String introduction;

    private String salt;

    private String goalType;

//    private LocalTime duration;

    private Float goalWeight;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Boolean getMember() {
        return isMember;
    }

    public void setMember(Boolean member) {
        isMember = member;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
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

//    public LocalTime getDuration() {
//        return duration;
//    }
//
//    public void setDuration(LocalTime duration) {
//        this.duration = duration;
//    }

    public Float getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(Float goalWeight) {
        this.goalWeight = goalWeight;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", registrationTime=" + registrationTime +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", isMember=" + isMember +
                ", isDelete=" + isDelete +
                ", iconURL='" + iconURL + '\'' +
                ", tags='" + tags + '\'' +
                ", introduction='" + introduction + '\'' +
                ", salt='" + salt + '\'' +
                ", goalType='" + goalType + '\'' +
                ", goalWeight=" + goalWeight +
                '}';
    }
}
