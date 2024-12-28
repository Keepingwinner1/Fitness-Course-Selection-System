package com.tongji.backend.entity.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
public class NewClassDTO {

    private Integer coachID;

    private Integer courseId;

    private Integer capacity;

    private Integer coursePrice;

    private LocalDateTime courseStartTime;

    private LocalDateTime courseEndTime;

    private String dayOfWeek;

}
