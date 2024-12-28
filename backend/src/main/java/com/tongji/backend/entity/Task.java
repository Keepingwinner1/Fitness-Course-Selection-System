package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskID", nullable = false)
    private Integer taskID;

    @NonNull
    @Column(name = "classID", nullable = false)
    private Integer classID;

    @NonNull
    @Column(name = "taskName", length = 20)
    private String taskName;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "publishTime")
    private LocalDateTime publishTime;

    @NonNull
    @Column(name = "endTime")
    private LocalDateTime endTime;

}