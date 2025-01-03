package com.tongji.backend.service;

import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ICoachService {

//    Coach addCoach(CoachDTO coach);
//
    Coach coachLogin(LoginDTO loginDTO);

    List<CourseClass> getTeachClass(int coachID);

    List<User> getClassStu(int classID);

    boolean createClass(NewClassDTO newClassDTO);

    Course createCourse(NewCourseDTO newCourseDTO);

    boolean modifyClass(Integer classID, NewClassDTO classDTO);

    boolean createTask(Task task);

    boolean modifyTask(Task task);

    boolean deleteTask(Integer taskID);

    void modifyCoach(CoachDTO coachDTO);

    List<CourseClass> getClassByCourseID(GetClassDTO getClassDTO);

    List<Course> getAllCourse(Integer coachID);

    Course getCourseByCourseID(int courseID);

    void applyForGym(CoachDTO coachDTO);
}
