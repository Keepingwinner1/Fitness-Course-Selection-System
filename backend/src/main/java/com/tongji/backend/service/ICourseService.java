package com.tongji.backend.service;

import com.tongji.backend.entity.Book;
import com.tongji.backend.entity.Payment;
import com.tongji.backend.entity.dto.*;

import java.util.List;

public interface ICourseService {

    // 获取所有课程，包括课程详情
    List<ClassDTO> getAllCourses();

    // 根据课程类型获取课程
    List<ClassDTO> getCoursesByType(String type);

    List<ClassDTO> getTodayCoursesByUser(Integer userID);

    // 获取用户正在参与的课程
    List<ClassDTO> getAllOngoingCoursesByUser(Integer userID);

    // 获取用户已完成的课程
    List<ClassDTO> getAllCompletedCoursesByUser(Integer userID);

    // 获取用户已预订的课程（加入购物车但未付款）
    List<ClassDTO> getAllBookedCoursesByUser(Integer userID);

    // 获取已付款但未开始的课程
    List<ClassDTO> getAllPaidCoursesByUser(Integer userID);

    // 获取用户已取消预订的课程
    List<ClassDTO> getAllCanceledCoursesByUser(Integer userID);

    List<ClassDTO> getAllcreateCLass(Integer gymID);

    // 用户预订课程（加入购物车）
    Book bookCourse(BookDTO bookDTO);

    // 用户支付课程
    Payment payForCourse(PaymentDTO paymentDTO);

    // 用户取消预订课程（移出购物车）
    void cancelBooking(Integer bookId);

    // 用户取消课程(退款)
    void cancelCourse(CancelDTO cancelDTO);

    // 课程结束后评价课程
    void evaluateCourse(EvaluationDTO evaluationDTO);

    void quitCourse(Integer classID, Integer userID);

    ClassAndStateDTO getClassByClassID(Integer classID);
}

