package com.tongji.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refundID", nullable = false)
    private Integer refundID;

    @NonNull
    @Column(name = "paymentID", nullable = false)
    private Integer paymentID;

    @NonNull
    @Column(name = "gymID", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "userID")
    private Integer userID;

    @NonNull
    @Column(name = "time")
    private LocalDateTime time;

    @NonNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NonNull
    @Column(name = "bookID")
    private Integer bookID;

}