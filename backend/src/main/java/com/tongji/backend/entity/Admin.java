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
    @Column(name = "adminID", nullable = false)
    private Integer id;

    @NonNull
    @Column(name = "userID")
    private Integer userID;

    @NonNull
    @Column(name = "gymID")
    private Integer gymID;

}