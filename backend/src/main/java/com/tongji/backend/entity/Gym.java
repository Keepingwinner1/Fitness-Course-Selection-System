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
@Table(name = "gym", schema = "course_system")
public class Gym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gym_id", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "gym_name")
    private String gymName;

    @NonNull
    @Column(name = "address")
    private String address;

    @NonNull
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

}