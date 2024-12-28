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
@Table(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id", nullable = false)
    private Integer coachID;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private Integer userID;

    @NonNull
    @Column(name = "real_name", length = 20)
    private String realName;

    @NonNull
    @Column(name = "id_number", length = 18)
    private String number;

    @NonNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NonNull
    @Column(name = "address", length = 100)
    private String address;

    @NonNull
    @Column(name = "card_id", length = 20)
    private String cardID;

    @NonNull
    @Column(name = "gym_id", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "register_time", nullable = false)
    private LocalDateTime registerTime;

    @NonNull
    @Column(name = "status")
    private Integer status;

}