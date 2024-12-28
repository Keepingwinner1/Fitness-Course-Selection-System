package com.tongji.backend.entity.dto;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StuDTO {
    private Integer userID;
    private String userName;
    private String iconURL;
}
