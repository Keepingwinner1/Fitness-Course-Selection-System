package com.tongji.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "advise")
public class Advise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advise_id", nullable = false, unique = true)
    private Integer adviseId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "coach_id")
    private Integer coachId;

    // Getters and Setters

    public Integer getAdviseId() {
        return adviseId;
    }

    public void setAdviseId(Integer adviseId) {
        this.adviseId = adviseId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }
}

