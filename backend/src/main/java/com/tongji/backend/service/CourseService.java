package com.tongji.backend.service;

import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ParticipateRepository participateRepository;
    @Autowired
    private GymRepository gymRepository;
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private AdviseRepository adviseRepository;
    @Autowired
    private TeachesRepository teachesRepository;
    @Autowired
    private  TaskRepository taskRepository;

    @Override
    public List<ClassDTO> getAllCourses() {
        LocalDateTime now = LocalDateTime.now();
        // 从 Class 表获取容量未满且结束时间小于当前时间的课程
        List<CourseClass> availableClasses = classRepository.findAll().stream()
                .filter(classEntity -> classEntity.getCapacity() > 0
                        && classEntity.getCourseEndTime().isAfter(now))
                .toList();

        // 根据 Class 表的课程 ID 获取对应的 Course 信息，并拼接为 ClassDTO
        return availableClasses.stream()
                .map(classEntity -> {
                    Course course = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程未找到，ID：" + classEntity.getCourseId()));

                    return mapToClassDTO(classEntity, course);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDTO> getCoursesByType(String type) {
        LocalDateTime now = LocalDateTime.now();

        // 获取所有课程
        List<ClassDTO> availableClasses = this.getAllCourses();

        // 根据 Class 表的课程 ID 获取对应的 Course 信息，筛选 type，并拼接为 ClassDTO
        return availableClasses.stream()
                .map(classEntity -> {
                    // 筛选课程类型是否匹配
                    if (!classEntity.getCourseType().equalsIgnoreCase(type)) {
                        return null; // 不符合条件，返回空
                    }
                    return classEntity;
                })
                .filter(Objects::nonNull) // 过滤掉返回空的记录
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDTO> getTodayCoursesByUser(Integer userID) {
        LocalDateTime now = LocalDateTime.now();
        int currentDayOfWeek = now.getDayOfWeek().getValue(); // 获取当前星期几（1=周一, ..., 7=周日）
        String dayOfWeekString = (currentDayOfWeek % 7) + ""; // 转换为 0=周日格式

        // 使用自定义查询从 participate 表 join class 表
        List<CourseClass> classes = classRepository.findTodayClassesByUserIdAndDayOfWeek(userID, dayOfWeekString,now);
        return classes.stream()
                .map(classEntity -> {
                    Course courseEntity = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程信息不完整"));
                    return mapToClassDTO(classEntity, courseEntity);
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<ClassDTO> getAllOngoingCoursesByUser(Integer userID) {

        LocalDateTime now = LocalDateTime.now();
        // 查询参与的课程且课程尚未结束
        List<CourseClass> classes = classRepository.findOngoingParticipatedByUser(userID, now);

        return classes.stream()
                .map(classEntity -> {
                    Course courseEntity = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程信息不完整"));
                    return mapToClassDTO(classEntity, courseEntity);
                })
                .collect(Collectors.toList());
    }

    public List<ClassDTO> getAllCompletedCoursesByUser(Integer userID) {
        LocalDateTime now = LocalDateTime.now(); // 当前日期时间

        // 查询条件：Participate 表与 Class 表关联，用户参与的课程，并且课程结束时间早于当前时间
        List<CourseClass> completedClasses = classRepository.findCompletedClassesByUser(userID, now);

        // 将查询结果转换为 ClassDTO
        return completedClasses.stream()
                .map(classEntity -> {
                    Course courseEntity = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程信息不完整"));
                    return mapToClassDTO(classEntity, courseEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDTO> getAllBookedCoursesByUser(Integer userID) {
        //从Book表获取user所有的bookID
        return classRepository.findBookedCoursesByUser(userID);
    }

    @Override
    public List<ClassDTO> getAllCanceledCoursesByUser(Integer userID) {
        List<CourseClass> classes = classRepository.findCanceledByUser(userID);

        return classes.stream()
                .map(classEntity -> {
                    Course courseEntity = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程信息不完整"));
                    return mapToClassDTO(classEntity, courseEntity);
                })
                .collect(Collectors.toList());
    }

    public List<ClassDTO> getAllPaidCoursesByUser(Integer userID){
        List<CourseClass> classes = classRepository.findPaidByUser(userID);

        return classes.stream()
                .map(classEntity -> {
                    Course courseEntity = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程信息不完整"));
                    return mapToClassDTO(classEntity, courseEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassDTO> getAllcreateCLass(Integer gymID){
        List<CourseClass> classes=classRepository.findByGymID(gymID);

        return classes.stream()
                .map(classEntity -> {
                    Course courseEntity = courseRepository.findById(classEntity.getCourseId())
                            .orElseThrow(() -> new IllegalArgumentException("课程信息不完整"));
                    return mapToClassDTO(classEntity, courseEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Book bookCourse(BookDTO bookDTO ) {
        Book book = new Book();
        book.setClassId(bookDTO.getClassId());
        book.setTraineeId(bookDTO.getTraineeId());
        book.setPaymentId(null);
        book.setPayMethod("default");
        book.setBookStatus(0); // 初始状态为未支付
        book.setBookTime(LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Payment payForCourse(PaymentDTO paymentDTO) {
        LocalDateTime now = LocalDateTime.now();

        // 1. 创建 Payment 实体并保存到数据库
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPayMethod(paymentDTO.getPayMethod());
        payment.setPaymentStatus(1); // 设置支付成功状态
        payment.setPayTime(now);
        //将paymnetDTO中的bookIdList转换为字符串，以#连接，采用循环遍历Integer转string连接实现
        StringBuilder bookIds = new StringBuilder();
        for(Integer id : paymentDTO.getBookIdList()){
            bookIds.append(id.toString());
            if(!id.equals(paymentDTO.getBookIdList().getLast()))
                bookIds.append("#");
        }
        payment.setBookIds(bookIds.toString());

        Payment savedPayment = paymentRepository.save(payment); // 保存并返回带有生成 paymentID 的对象
        Integer paymentId = savedPayment.getPaymentId();

        // 2. 更新 Book 表中的记录
        List<Integer> bookIdList = paymentDTO.getBookIdList();
        bookIdList.forEach(bookId -> {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("预订记录不存在，bookId: " + bookId));
            book.setBookStatus(1); // 设置支付成功状态
            book.setPayMethod(paymentDTO.getPayMethod());
            book.setPaymentId(paymentId); // 设置 paymentID
            bookRepository.save(book); // 更新记录

            // 3. 向 Participate 表插入记录
            Participate participate = new Participate();
            participate.setTraineeId(book.getTraineeId());
            participate.setClassId(book.getClassId());
            participate.setGrade(null); // 默认未评分
            participate.setEvaluate(null); // 默认无评价
            participateRepository.save(participate); // 保存到数据库

            //4. 插入Advise表
            Advise advise = new Advise();
            advise.setUserId(book.getTraineeId());
            advise.setClassId(book.getClassId());
            //根据classid查询课程的教练id
            int coachID = teachesRepository.findByClassId(book.getClassId()).getFirst().getCoachID();
            //打印coachID
            System.out.println("coachID:"+coachID);
            advise.setCoachId(coachID);
            adviseRepository.save(advise);
            //5.Class表的capacity-1
            classRepository.updateCapacity(book.getClassId(),-1);
        });
        return savedPayment;
    }

    @Override
    public void cancelBooking(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("预订不存在"));
        bookRepository.delete(book);
    }

    @Override
    @Transactional
    public void cancelCourse(CancelDTO cancelDTO) {
        // 1. 检查 classID 是否存在并获取课程信息
        CourseClass courseClass = classRepository.findById(cancelDTO.getClassID())
                .orElseThrow(() -> new IllegalArgumentException("班级不存在，classID: " + cancelDTO.getClassID()));
        // 检查当前日期是否在课程开始时间之前，若不在，进入审核环节
        if (LocalDateTime.now().isAfter(courseClass.getCourseStartTime())) {
            //抛出异常
            throw new IllegalArgumentException("课程开始时间已过，请先进行退款申请");
        }

        // 2. 更新 Book 表中与该课程相关的记录，将 bookStatus 设置为已取消 (2)
        Book book = bookRepository.findByClassIdAndStatus(cancelDTO.getClassID(),1);
        book.setBookStatus(2); // 设置为已取消(已退款)
        bookRepository.save(book); // 更新记录

        // 3. 删除Advise表记录
        //根据userID与classID查询删除记录
        Advise advise = adviseRepository.findByClassIdAndUserId(cancelDTO.getClassID(), book.getTraineeId());
        if (advise != null) {
            adviseRepository.delete(advise);
        } else {
            // 处理未找到记录的情况，可以记录日志或抛出异常
            System.out.println("未找到需要删除的 Advise 记录，classID: " +cancelDTO.getClassID()+ "userID: " +book.getTraineeId());
        }

        // 4. 更新 Payment 表中与该课程相关的支付记录，设置 paymentStatus 为已退款 (3)
        Integer paymentId = book.getPaymentId();
        if (paymentId != null) {
            Payment payment = paymentRepository.findById(paymentId)
                        .orElseThrow(() -> new IllegalArgumentException("支付记录不存在，paymentId: " + paymentId));
            payment.setPaymentStatus(3); // 设置为已退款
            paymentRepository.save(payment); // 更新记录
        };
        // 5. 删除 Participate 表中与该课程相关的记录
        Participate participate = participateRepository.findByClassIdAndTraineeId(cancelDTO.getClassID(), book.getTraineeId());
        if (participate == null) {
            //打印传入与返回信息
            System.out.println("传入的classID:"+cancelDTO.getClassID()+"与传入的traineeID:"+book.getTraineeId());
        }
        else
            participateRepository.delete(participate);

        //6. class表的capacity+1
        classRepository.updateCapacity(cancelDTO.getClassID(),1);
    }


    @Override
    public void evaluateCourse(EvaluationDTO evaluationDTO) {
        // 1. 查找参与记录，确保当前学员已经参与了该课程
        Participate participate = participateRepository.findByClassIdAndTraineeId(
                evaluationDTO.getClassId(),
                evaluationDTO.getTraineeId());

        if (participate == null) {
            throw new IllegalArgumentException("未找到该学员参与的课程记录");
        }

        // 2. 更新评分和评价
        participate.setGrade(evaluationDTO.getGrade());
        participate.setEvaluate(evaluationDTO.getEvaluate());

        // 3. 保存更新后的参与记录
        participateRepository.save(participate);
    }

    @Override
    @Transactional
    public void quitCourse(Integer classID, Integer userID){
        Book book = bookRepository.findByClassId(classID);
        if(book!=null){
            Integer gymID=gymRepository.findByBookID(book.getBookId());
            Payment payment=new Payment();
            payment.setPaymentStatus(3);
            payment.setPayMethod(null);
            payment.setPayTime(LocalDateTime.now());
            payment.setAmount(classRepository.findById(book.getClassId()).orElseThrow(()->new RuntimeException("未找到课程")).getCoursePrice());
            var p =paymentRepository.save(payment);
            refundRepository.save(new Refund(p.getPaymentId(),gymID,userID,LocalDateTime.now(),0,book.getClassId()));
        }
    }

    @Override
    public ClassAndStateDTO getClassByClassID(Integer classID) {
        if(classRepository.findById(classID).isPresent()){
            CourseClass courseClass = classRepository.findById(classID).get();
            //判断当前时间与课程结束时间，课程开始时间的大小关系，并赋值state为notStarted,ongoing,completed
            String state = "";
            if(LocalDateTime.now().isBefore(courseClass.getCourseStartTime())){
                state = "notStarted";
            }
            else if(LocalDateTime.now().isAfter(courseClass.getCourseStartTime())&&LocalDateTime.now().isBefore(courseClass.getCourseEndTime())){
                state = "ongoing";
            }
            else if(LocalDateTime.now().isAfter(courseClass.getCourseEndTime())){
                state = "completed";
            }
            Course course = courseRepository.findById(courseClass.getCourseId()).get();
            return mapToClassAndStateDTO(courseClass,course,state);
        }
        else throw new RuntimeException("未找到课程");
    }

    @Override
    public List<Task> getAllTasks(Integer classID) {
        return taskRepository.findByClassID(classID);
    }



    private ClassAndStateDTO mapToClassAndStateDTO(CourseClass classEntity, Course courseEntity, String state) {
        ClassAndStateDTO classDTO = new ClassAndStateDTO();
        classDTO.setState(state);
        classDTO.setClassId(classEntity.getClassId());
        classDTO.setCourseType(courseEntity.getCourseType());
        classDTO.setCourseName(courseEntity.getCourseName());
        classDTO.setCourseDescription(courseEntity.getCourseDescription());
        classDTO.setCourseGrade(courseEntity.getCourseGrade());
        classDTO.setCoursePhotoUrl(courseEntity.getCoursePhotoUrl());
        classDTO.setCapacity(classEntity.getCapacity());
        classDTO.setCoursePrice(classEntity.getCoursePrice());
        classDTO.setCourseStartTime(classEntity.getCourseStartTime());
        classDTO.setCourseEndTime(classEntity.getCourseEndTime());
        classDTO.setDayOfWeek(classEntity.getDayOfWeek());
        return classDTO;
    }

    private ClassDTO mapToClassDTO(CourseClass classEntity, Course courseEntity) {
        ClassDTO classDTO = new ClassDTO();
        classDTO.setClassId(classEntity.getClassId());
        classDTO.setCourseType(courseEntity.getCourseType());
        classDTO.setCourseName(courseEntity.getCourseName());
        classDTO.setCourseDescription(courseEntity.getCourseDescription());
        classDTO.setCourseGrade(courseEntity.getCourseGrade());
        classDTO.setCoursePhotoUrl(courseEntity.getCoursePhotoUrl());
        classDTO.setCapacity(classEntity.getCapacity());
        classDTO.setCoursePrice(classEntity.getCoursePrice());
        classDTO.setCourseStartTime(classEntity.getCourseStartTime());
        classDTO.setCourseEndTime(classEntity.getCourseEndTime());
        classDTO.setDayOfWeek(classEntity.getDayOfWeek());
        return classDTO;
    }
}

