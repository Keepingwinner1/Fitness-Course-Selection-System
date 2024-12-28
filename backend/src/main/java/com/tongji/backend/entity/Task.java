package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema = "course_system")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private Integer taskID;

    @NonNull
    @Column(name = "class_id", nullable = false)
    private Integer classID;

    @NonNull
    @Column(name = "task_name", length = 20)
    private String taskName;

    @NonNull
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    @NonNull
    @Column(name = "end_time")
    private LocalDateTime endTime;

}