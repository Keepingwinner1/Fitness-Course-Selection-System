package com.tongji.backend.controller;

import com.tongji.backend.entity.Book;
import com.tongji.backend.entity.Payment;
import com.tongji.backend.entity.dto.*;

import com.tongji.backend.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    // 获取所有课程，包括课程详情
    @GetMapping
    public ResponseMessage<List<ClassDTO>> getAllCourses() {
        List<ClassDTO> courses = courseService.getAllCourses();
        return ResponseMessage.success(courses);
    }

    // 根据课程类型获取课程
    @GetMapping("/type/{type}")
    public ResponseMessage<List<ClassDTO>> getCoursesByType(@PathVariable String type) {
        List<ClassDTO> courses = courseService.getCoursesByType(type);
        return ResponseMessage.success(courses);
    }

    // 获取用户今天要上的课程
    @GetMapping("/user/{userID}/today")
    public ResponseMessage<List<ClassDTO>> getTodayCoursesByUser(@PathVariable Integer userID) {
        List<ClassDTO> courses = courseService.getTodayCoursesByUser(userID);
        return ResponseMessage.success(courses);
    }

    // 获取用户正在参与的课程
    @GetMapping("/user/{userID}/ongoing")
    public ResponseMessage<List<ClassDTO>> getAllOngoingCoursesByUser(@PathVariable Integer userID) {
        List<ClassDTO> courses = courseService.getAllOngoingCoursesByUser(userID);
        return ResponseMessage.success(courses);
    }

    // 获取用户已完成的课程
    @GetMapping("/user/{userID}/completed")
    public ResponseMessage<List<ClassDTO>> getAllCompletedCoursesByUser(@PathVariable Integer userID) {
        List<ClassDTO> courses = courseService.getAllCompletedCoursesByUser(userID);
        return ResponseMessage.success(courses);
    }

    // 获取用户已预订但未付款的课程
    @GetMapping("/user/{userID}/booked")
    public ResponseMessage<List<ClassDTO>> getAllBookedCoursesByUser(@PathVariable Integer userID) {
        List<ClassDTO> courses = courseService.getAllBookedCoursesByUser(userID);
        return ResponseMessage.success(courses);
    }

    // 获取已付款但未开始的课程
    @GetMapping("/user/{userID}/paid")
    public ResponseMessage<List<ClassDTO>> getAllPaidCoursesByUser(@PathVariable Integer userID) {
        List<ClassDTO> courses = courseService.getAllPaidCoursesByUser(userID);
        return ResponseMessage.success(courses);
    }

    // 获取用户已取消的课程
    @GetMapping("/user/{userID}/canceled")
    public ResponseMessage<List<ClassDTO>> getAllCanceledCoursesByUser(@PathVariable Integer userID) {
        List<ClassDTO> courses = courseService.getAllCanceledCoursesByUser(userID);
        return ResponseMessage.success(courses);
    }



    // 用户预订课程（加入购物车）
    @PostMapping("/book")
    public ResponseMessage<Book> bookCourse(@RequestBody BookDTO bookDTO) {
        Book bookedCourse = courseService.bookCourse(bookDTO);
        return ResponseMessage.success(bookedCourse);
    }

    // 用户支付课程
    @PostMapping("/pay")
    public ResponseMessage<Payment> payForCourse(@RequestBody PaymentDTO paymentDTO) {
        Payment payment = courseService.payForCourse(paymentDTO);
        return ResponseMessage.success(payment);
    }

    // 用户取消预订课程（移出购物车）
    @DeleteMapping("/book/{bookId}")
    public ResponseMessage<Void> cancelBooking(@PathVariable Integer bookId) {
        courseService.cancelBooking(bookId);
        return ResponseMessage.success(null);
    }

    // 用户取消课程(退款)
    @DeleteMapping("/course/{classID}")
    public ResponseMessage<Void> cancelCourse(@PathVariable Integer classID) {
        courseService.cancelCourse(classID);
        return ResponseMessage.success(null);
    }

    // 课程结束后评价课程
    @PostMapping("/evaluate")
    public ResponseMessage<Void> evaluateCourse(@RequestBody EvaluationDTO evaluationDTO) {
        courseService.evaluateCourse(evaluationDTO);
        return ResponseMessage.success(null);
    }

    @PutMapping("/quitCourse/{userID}/{bookID}")
    public ResponseMessage<String> quitCourse(@PathVariable Integer userID, @PathVariable Integer bookID) {
        try {
            courseService.quitCourse(bookID, userID);
            return ResponseMessage.successmsg("已发送退款申请");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }
}





//    @PostMapping
//    public ResponseMessage<Course> addCourse(@RequestBody CourseDTO courseDTO) {
//        Course course = courseService.addCourse(courseDTO);
//        return ResponseMessage.success(course);
//    }

//    @PutMapping
//    public ResponseMessage<Course> editCourse(@RequestBody CourseDTO courseDTO) {
//        Course course = courseService.editCourse(courseDTO);
//        return ResponseMessage.success(course);
//    }
//
//    @DeleteMapping("/{courseID}")
//    public ResponseMessage<Course> deleteCourse(@PathVariable Integer courseID) {
//        courseService.deleteCourse(courseID);
//        return ResponseMessage.success(null);
//    }


