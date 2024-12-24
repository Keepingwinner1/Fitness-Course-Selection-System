package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "gymID", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "gymName")
    private String gymName;

    @NonNull
    @Column(name = "address")
    private String address;

}