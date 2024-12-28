package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin", schema = "course_system")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "user_id")
    private Integer userID;

    @NonNull
    @Column(name = "gym_id")
    private Integer gymID;

}