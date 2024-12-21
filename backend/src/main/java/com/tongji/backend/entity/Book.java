package com.tongji.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, unique = true)
    private Integer bookId;

    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "trainee_id")
    private Integer traineeId;

    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "book_status")
    private Boolean bookStatus;

    @Column(name = "book_time")
    private LocalDateTime bookTime;

    // Getters and Setters
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Boolean getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Boolean bookStatus) {
        this.bookStatus = bookStatus;
    }

    public LocalDateTime getBookTime() {
        return bookTime;
    }

    public void setBookTime(LocalDateTime bookTime) {
        this.bookTime = bookTime;
    }
}

