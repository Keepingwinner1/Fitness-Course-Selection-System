package com.tongji.backend.service;


import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private CoursePublishRepository coursePublishRepository;


    @Override
    public Coach coachLogin(LoginDTO loginDTO) {
        UserDTO userDTO= userService.login(loginDTO);
        return coachRepository.findByUserID(userDTO.getUserID());
    }

    @Override
    public List<CourseClass> getTeachClass(int coachID) {
        return classRepository.findTeachClass(coachID);
    }

    @Override
    public List<User> getClassStu(int classID){
        return userRepository.getClassSTU(classID);
    }

    @Override
    @Transactional
    public boolean createClass(NewClassDTO newClassDTO) {
        if(coachRepository.existsCoachByStatus(newClassDTO.getCoachID())) {
            CourseClass courseClass = new CourseClass();
            BeanUtils.copyProperties(newClassDTO, courseClass);
            courseClass.setStatus(0);
            var c = classRepository.save(courseClass);
            teachesRepository.save(new Teaches(newClassDTO.getCoachID(), c.getClassId()));
            return true;
        }
        else {
            throw new IllegalArgumentException("无权创建班级");
        }
    }

    @Override
    @Transactional
    public Course createCourse(NewCourseDTO newCourseDTO) {
        if (coachRepository.existsCoachByStatus(newCourseDTO.getCoachID())) {
            Course course = new Course();
            course.setCourseGrade(0);
            BeanUtils.copyProperties(newCourseDTO, course);
            var c = courseRepository.save(course);
            coursePublishRepository.save(new CoursePublish(newCourseDTO.getCoachID(), c.getCourseId()));
            return course;
        }
        else{
            throw new IllegalArgumentException("无权发布课程");
        }
    }
    @Transactional
    @Override
    public boolean modifyClass(Integer classID, NewClassDTO classDTO){
        CourseClass courseClass = new CourseClass();
        BeanUtils.copyProperties(classDTO, courseClass);
        courseClass.setClassId(classID);
        if(!teachesRepository.existsTeachesByClassID(courseClass.getClassId())){
            throw new RuntimeException("要修改的班级不存在");
        }
        Optional<CourseClass> a= classRepository.findById(classID);
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
        Coach coach=coachRepository.findByUserID(coachDTO.getUserID());
        if(coach!=null){
            BeanUtils.copyProperties(coachDTO, coach);
            coachRepository.save(coach);
        }
        else{
            throw new RuntimeException("找不到教练信息");
        }
    }

    @Override
    @Transactional
    public List<CourseClass> getClassByCourseID(GetClassDTO getClassDTO) {
        //从Teaches表获取coachID所有的classID
        return classRepository.findByCoachAndCourse(getClassDTO.getCoachID(), getClassDTO.getCourseID());
    }

    @Override
    public List<Course> getAllCourse(Integer coachID){
        return courseRepository.findByCoachID(coachID);
    }

    @Override
    public Course getCourseByCourseID(int courseID) {
        return courseRepository.findById(courseID).orElse(null);
    }

    @Override
    public void applyForGym(CoachDTO coachDTO) {
        if(coachRepository.existsCoachByCoachID(coachDTO.getUserID(),coachDTO.getGymID())){
            throw new RuntimeException("该教练已申请过该健身房");
        }
        else{
            Coach coach=coachRepository.findByUserID(coachDTO.getUserID());
            BeanUtils.copyProperties(coachDTO, coach);
            coachRepository.save(coach);
        }
    }
}
