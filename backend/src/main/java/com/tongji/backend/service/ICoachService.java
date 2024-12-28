package com.tongji.backend.service;

import com.tongji.backend.entity.Coach;
import com.tongji.backend.entity.Course;
import com.tongji.backend.entity.CourseClass;
import com.tongji.backend.entity.Task;
import com.tongji.backend.entity.dto.*;

import java.util.List;

public interface ICoachService {

    Coach addCoach(CoachDTO coach);

    Coach coachLogin(LoginDTO loginDTO);

    List<CourseClass> getTeachClass(int coachID);

    List<StuDTO> getClassStu(int classID);

    boolean createClass(NewClassDTO newClassDTO);

    boolean createCourse(NewCourseDTO newCourseDTO);

    boolean modifyClass(ClassDTO classDTO);

    boolean createTask(Task task);

    boolean modifyTask(Task task);

    boolean deleteTask(Integer taskID);

    void modifyCoach(CoachDTO coachDTO);
}
