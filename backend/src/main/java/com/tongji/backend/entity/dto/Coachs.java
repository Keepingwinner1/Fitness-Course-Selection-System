package com.tongji.backend.entity.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Coachs {
    private Integer coachID;

    private Integer userID;

    private String realName;

    private String number;

    private String phone;

    private String address;

    private String cardID;

    private Integer gymID;

    private LocalDateTime registerTime;

    private Integer status;

    private String token;
}
