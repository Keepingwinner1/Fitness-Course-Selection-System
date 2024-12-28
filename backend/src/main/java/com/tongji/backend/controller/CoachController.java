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


//    @PostMapping("/register")
//    public ResponseMessage<Coach> register(@RequestBody CoachDTO coach) {
//        return ResponseMessage.success(coachService.addCoach(coach));
//    }

    @GetMapping("/login")
    public ResponseMessage<Coach> login(@RequestBody LoginDTO loginDTO) {
        Coach coach =coachService.coachLogin(loginDTO);
        if(coach.getStatus()!=0) {
            return ResponseMessage.success(coach);
        }
        else{
            return ResponseMessage.error("教练申请还未通过");
        }
    }
    @GetMapping("/getAllClass/{coachID}")
    public ResponseMessage<List<CourseClass>> getAllClass(@PathVariable int coachID) {
        try {
            return ResponseMessage.success(coachService.getTeachClass(coachID));
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }
    @GetMapping("/getClassStu/{classID}")
    public ResponseMessage<List<StuDTO>> getClassStu(@PathVariable int classID) {
        try {
            return ResponseMessage.success(coachService.getClassStu(classID));
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/createClass")
    public ResponseMessage<String> createClass(@RequestBody NewClassDTO newClassDTO) {
        try {
            coachService.createClass(newClassDTO);
            return ResponseMessage.success("新班级创建成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/createCourse")
    public ResponseMessage<String> createCourse(@RequestBody NewCourseDTO courseDTO) {
        try {
            coachService.createCourse(courseDTO);
            return ResponseMessage.success("新课程创建成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/modifyClass/{classID}")
    public ResponseMessage<String> modifyClass(@PathVariable Integer classID,@RequestBody NewClassDTO classDTO) {
        try {
            coachService.modifyClass(classID,classDTO);
            return ResponseMessage.success("课程信息修改成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/createTask")
    public ResponseMessage<String> createTask(@RequestBody Task task){
        try {
            coachService.createTask(task);
            return ResponseMessage.success("作业发布成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PostMapping("/modifyTask")
    public ResponseMessage<String> modifyTask(@RequestBody Task task){
        try{
            coachService.modifyTask(task);
            return ResponseMessage.success("任务修改成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTask/{taskID}")
    public ResponseMessage<String> deleteTask( @PathVariable Integer taskID){
        try{
            coachService.deleteTask(taskID);
            return ResponseMessage.success("任务删除成功");
        }
        catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PutMapping("/modifyCoach")
    public ResponseMessage<String> modifyCoach(@RequestBody CoachDTO coachDTO){
        try{
            coachService.modifyCoach(coachDTO);
            return ResponseMessage.success("信息修改成功");
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }
}
