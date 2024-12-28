package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
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

    public Admin(int userID, int gymID) {
        this.userID = userID;
        this.gymID = gymID;
    }

}