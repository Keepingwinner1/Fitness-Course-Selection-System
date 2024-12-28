package com.tongji.backend.service;


import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.repository.*;
import jakarta.transaction.Transactional;
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
    @Autowired
    private TeachesRepository teachesRepository;

    @Override
    public Coach addCoach(CoachDTO coachDTO) {
        Coach coach = new Coach();
        BeanUtils.copyProperties(coachDTO, coach);
        coach.setRegisterTime(LocalDateTime.now());
        coach.setStatus(0);
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
        var c=classRepository.save(courseClass);
        teachesRepository.save(new Teaches(c.getClassId(), newClassDTO.getCoachID()));
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
    @Transactional
    public boolean modifyClass(ClassDTO classDTO){
        CourseClass courseClass = new CourseClass();
        BeanUtils.copyProperties(classDTO, courseClass);
        if(!teachesRepository.existsTeachesByClassID(courseClass.getClassId())){
            throw new RuntimeException("要修改的班级不存在");
        }
        Optional<CourseClass> a= classRepository.findById(classDTO.getClassId());
        if(a.isPresent()){
            courseClass.setCoursePrice(a.get().getCoursePrice());
            courseClass.setStatus(a.get().getStatus());
            classRepository.save(courseClass);
            return true;
        }
        else{
            throw new RuntimeException("出现未知错误");
        }
    }

    @Override
    public boolean createTask(Task task){
        task.setTaskID(null);
        taskRepository.save(task);
        return true;
    }

    @Override
    public boolean modifyTask(Task task){
        if(!taskRepository.existsById(task.getTaskID())){
            throw new RuntimeException("要修改的任务不存在");
        }
        taskRepository.save(task);
        return true;
    }

    @Override
    public boolean deleteTask(Integer taskID){
        taskRepository.deleteById(taskID);
        return true;
    }

    @Override
    public void modifyCoach(CoachDTO coachDTO) {
        Optional<Coach> coach=coachRepository.findByUserID(coachDTO.getUserID());
        if(coach.isPresent()){
            Coach coach1=coach.get();
            BeanUtils.copyProperties(coachDTO, coach1);
            coachRepository.save(coach1);
        }
        else{
            throw new RuntimeException("找不到教练信息");
        }
    }
}
