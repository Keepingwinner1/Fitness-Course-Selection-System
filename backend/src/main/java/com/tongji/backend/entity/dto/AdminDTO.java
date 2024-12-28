package com.tongji.backend.entity.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

@Data
public class AdminDTO {
    private Integer id;

    private Integer userID;

    private Integer gymID;

    private String token;
}
