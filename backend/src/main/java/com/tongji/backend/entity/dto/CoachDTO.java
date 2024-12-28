package com.tongji.backend.entity.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CoachDTO {

    private Integer userID;

    private String realName;

    private String number;

    private String phone;

    private String address;

    private String cardID;

    private Integer gymID;
}
