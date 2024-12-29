package com.tongji.backend.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CoachRetDTO {
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
