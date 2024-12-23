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
@Table(name = "coach", schema = "course_system")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coachID", nullable = false)
    private Integer coachID;

    @NonNull
    @Column(name = "userID", nullable = false)
    private Integer userID;

    @NonNull
    @Column(name = "realName", length = 20)
    private String realName;

    @NonNull
    @Column(name = "IDnumber", length = 18)
    private String IDNumber;

    @NonNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NonNull
    @Column(name = "address", length = 100)
    private String address;

    @NonNull
    @Column(name = "cardID", length = 20)
    private String cardID;

    @NonNull
    @Column(name = "gymID", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "registerTime", nullable = false)
    private LocalDateTime registerTime;

    @NonNull
    @Column(name = "status", nullable = false)
    private Integer status;

}