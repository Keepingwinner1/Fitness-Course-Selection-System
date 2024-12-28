package com.tongji.backend.entity.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
@AllArgsConstructor
public class GymDTO {

    private String gymName;

    private String address;

}
