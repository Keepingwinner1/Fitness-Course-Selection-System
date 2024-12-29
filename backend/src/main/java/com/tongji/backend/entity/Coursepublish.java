package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coursepublish")
public class Coursepublish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "coach_id")
    private Integer coachID;

    @NonNull
    @Column(name = "course_id")
    private Integer courseID;

}