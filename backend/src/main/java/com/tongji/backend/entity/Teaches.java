package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teaches", schema = "course_system")
public class Teaches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "coachID")
    private Integer coachID;

    @NonNull
    @Column(name = "classID", nullable = false)
    private Integer classID;

}