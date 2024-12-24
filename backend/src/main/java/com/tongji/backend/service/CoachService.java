package com.tongji.backend.service;


import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService implements ICoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Coach addCoach(CoachDTO coachDTO) {
        Coach coach = new Coach();
        BeanUtils.copyProperties(coachDTO, coach);
        coach.setRegisterTime(LocalDateTime.now());
        return coachRepository.save(coach);
    }

    @Override
    public Coach coachLogin(LoginDTO loginDTO) {
        User user= userService.login(loginDTO);
        return coachRepository.findByUserID(user.getUserID())
                .orElseThrow(()->new IllegalArgumentException("教练不存在"));

    }

    @Override
    public List<CourseClass> getTeachClass(int coachID) {
        return classRepository.findTeachClass(coachID);
    }

    @Override
    public List<StuDTO> getClassStu(int classID){
        return userRepository.getClassSTU(classID);
    }

    @Override
    public boolean createClass(NewClassDTO newClassDTO) {
        CourseClass courseClass = new CourseClass();
        BeanUtils.copyProperties(newClassDTO, courseClass);
        courseClass.setStatus(0);
        classRepository.save(courseClass);
        return true;
    }

    @Override
    public boolean createCourse(NewCourseDTO newCourseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(newCourseDTO, course);
        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean modifyClass(ClassDTO classDTO){
        CourseClass courseClass = new CourseClass();
        BeanUtils.copyProperties(classDTO, courseClass);
        Optional<CourseClass> a= classRepository.findById(classDTO.getClassId());
        if(a.isPresent()){
            courseClass.setCoursePrice(a.get().getCoursePrice());
            courseClass.setStatus(a.get().getStatus());
            classRepository.save(courseClass);
            return true;
        }
        else{
            throw new IllegalArgumentException("要修改的班级不存在");
        }
    }

    @Override
    public boolean createTask(Task task){
        task.setTaskID(null);
        taskRepository.save(task);
        return true;
    }
}
