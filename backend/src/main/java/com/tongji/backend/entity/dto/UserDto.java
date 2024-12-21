package com.tongji.backend.entity.dto;
import java.time.LocalDateTime;


public class UserDto {

    private Integer userID;
    private String userName;

    private String password;

    private String email;

    private LocalDateTime registrationTime;

    private Integer age;

    private String gender;

    private String iconURL;

    private String tags;

    private String introduction;

    private String salt;

    private String goalType;

    private Float goalWeight;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getTags() {
        return tags;
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

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
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
                ", iconURL='" + iconURL + '\'' +
                ", tags='" + tags + '\'' +
                ", introduction='" + introduction + '\'' +
                ", salt='" + salt + '\'' +
                ", goalType='" + goalType + '\'' +
                ", goalWeight=" + goalWeight +
                '}';
    }
}
