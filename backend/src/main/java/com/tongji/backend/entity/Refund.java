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
    @Column(name = "refund_id", nullable = false)
    private Integer refundID;

    @NonNull
    @Column(name = "payment_id", nullable = false)
    private Integer paymentID;

    @NonNull
    @Column(name = "gym_id", nullable = false)
    private Integer gymID;

    @NonNull
    @Column(name = "user_id")
    private Integer userID;

    @NonNull
    @Column(name = "time")
    private LocalDateTime time;

    @NonNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NonNull
    @Column(name = "book_id")
    private Integer bookID;

}