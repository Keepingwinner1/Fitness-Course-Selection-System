package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coachID", nullable = false)
    private Integer coachID;

    @NonNull
    @Column(name = "userID", nullable = false)
    private Integer userID;

    @NonNull
    @Column(name = "realName", length = 20)
    private String realName;

    @NonNull
    @Column(name = "IDnumber", length = 18)
    private String IDNumber;

    @NonNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NonNull
    @Column(name = "address", length = 100)
    private String address;

    @NonNull
    @Column(name = "cardID", length = 20)
    private String cardID;

    @NonNull
    @Column(name = "gymID", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "registerTime", nullable = false)
    private LocalDateTime registerTime;

    @NonNull
    @Column(name = "status", nullable = false)
    private Integer status;

    public Integer getCoachID() {
        return coachID;
    }

    public void setCoachID(Integer coachID) {
        this.coachID = coachID;
    }

    public @NonNull Integer getUserID() {
        return userID;
    }

    public void setUserID(@NonNull Integer userID) {
        this.userID = userID;
    }

    public @NonNull String getRealName() {
        return realName;
    }

    public void setRealName(@NonNull String realName) {
        this.realName = realName;
    }

    public @NonNull String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(@NonNull String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public @NonNull String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public @NonNull String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public @NonNull String getCardID() {
        return cardID;
    }

    public void setCardID(@NonNull String cardID) {
        this.cardID = cardID;
    }

    public @NonNull Integer getGymID() {
        return gymID;
    }

    public void setGymID(@NonNull Integer gymID) {
        this.gymID = gymID;
    }

    public @NonNull LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(@NonNull LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public @NonNull Integer getStatus() {
        return status;
    }

    public void setStatus(@NonNull Integer status) {
        this.status = status;
    }
}