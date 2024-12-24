package com.tongji.backend.controller;


import com.tongji.backend.entity.Coach;
import com.tongji.backend.entity.CourseClass;
import com.tongji.backend.entity.Task;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;


    @PostMapping("/register")
    public ResponseMessage<Coach> register(@RequestBody CoachDTO coach) {
        return ResponseMessage.success(coachService.addCoach(coach));
    }

    @PostMapping("/login")
    public ResponseMessage<Coach> login(@RequestBody LoginDTO loginDTO) {
        return ResponseMessage.success(coachService.coachLogin(loginDTO));
    }

    @GetMapping("/getAllClass/{coachID}")
    public ResponseMessage<List<CourseClass>> getAllClass(@PathVariable int coachID) {
        return ResponseMessage.success(coachService.getTeachClass(coachID));
    }

    @GetMapping("/getClassStu/{classID}")
    public ResponseMessage<List<StuDTO>> getClassStu(@PathVariable int classID) {
        return ResponseMessage.success(coachService.getClassStu(classID));
    }

    @PostMapping("/createClass")
    public ResponseMessage<String> createClass(@RequestBody NewClassDTO newClassDTO) {
        try {
            coachService.createClass(newClassDTO);
            return ResponseMessage.successmsg("新班级创建成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/createCourse")
    public ResponseMessage<String> createCourse(@RequestBody NewCourseDTO courseDTO) {
        try {
            coachService.createCourse(courseDTO);
            return ResponseMessage.successmsg("新课程创建成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/modifyClass")
    public ResponseMessage<String> modifyClass(@RequestBody ClassDTO classDTO) {
        try {
            coachService.modifyClass(classDTO);
            return ResponseMessage.successmsg("课程信息修改成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/createTask")
    public ResponseMessage<String> createTask(@RequestBody Task task){
        try {
            coachService.createTask(task);
            return ResponseMessage.successmsg("作业发布成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }
}