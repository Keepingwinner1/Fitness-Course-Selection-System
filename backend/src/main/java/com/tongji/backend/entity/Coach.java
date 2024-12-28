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
    @Column(name = "coach_id", nullable = false)
    private Integer coachID;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private Integer userID;

    @NonNull
    @Column(name = "real_name", length = 20)
    private String realName;

    @NonNull
    @Column(name = "id_number", length = 18)
    private String IDNumber;

    @NonNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NonNull
    @Column(name = "address", length = 100)
    private String address;

    @NonNull
    @Column(name = "card_id", length = 20)
    @Column(name = "card_id", length = 20)
    private String cardID;

    @NonNull
    @Column(name = "gym_id")
    @Column(name = "gym_id", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "register_time", nullable = false)
    private LocalDateTime registerTime;

    @NonNull
    @Column(name = "status")
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