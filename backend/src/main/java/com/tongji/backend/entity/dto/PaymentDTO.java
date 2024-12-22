package com.tongji.backend.entity.dto;

import java.util.List;

public class PaymentDTO {

    private List<Integer> bookIdList; // 关联 Book
    private Integer userId;
    private Integer amount;  // 支付金额
    private String payMethod; // 支付方式，可能值：WechatPay, AliPay
    //private Integer paymentStatus; // 支付状态：-1 待处理，1 成功，0 失败，3 已退款

    // Getters and Setters
    public List<Integer> getBookIdList() {
        return bookIdList;
    }

    public void setBookIdList(List<Integer> bookIdList) {
        this.bookIdList = bookIdList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

}