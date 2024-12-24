package com.tongji.backend.service;


import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.LoginDTO;
import com.tongji.backend.entity.dto.ProcessDTO;
import com.tongji.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

     @Autowired
     AdminRepository adminRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private ParticipateRepository participateRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private TeachesRepository teachesRepository;

     @Override
     public Admin addAdmin(Admin admin) {
         return adminRepository.save(admin);
     }

     @Override
     public Admin login(LoginDTO loginDTO){
          User user=userService.login(loginDTO);
         return adminRepository.findByUserID(user.getUserID());
     }

     @Override
     public List<Payment> getAllRefund(Integer gymID) {
         return paymentRepository.getAllRefunds(gymID);
     }

     @Override
     public List<Coach> getAllApplyCoach(Integer gymID){
          return coachRepository.findAllByStatusAndGymID(0,gymID);
     }

     @Override
     @Transactional
     public boolean proRefund(ProcessDTO processDTO){
          Optional<Refund> refund=refundRepository.findById(processDTO.getProcessId());
          if(refund.isPresent()){
               refund.get().setStatus(processDTO.isHandle()?1:2);
               refundRepository.save(refund.get());
          }
          else {
               throw new RuntimeException("未找到退款申请记录");
          }
          var r=refund.get();
          Optional<Payment> payment=paymentRepository.findById(r.getPaymentID());
          if(payment.isPresent()){
               payment.get().setPaymentStatus(processDTO.isHandle()?3:0);
               paymentRepository.save(payment.get());
          }
          else {
               throw new RuntimeException("未找到交易记录");
          }
          if(processDTO.isHandle()){
               Optional<Book> courseClass=bookRepository.findById(r.getBookID());
               if(courseClass.isPresent()){
                    participateRepository.deleteParticipateByClassIdAndTraineeId(courseClass.get().getClassId(),r.getUserID());
               }
               else{
                    throw new RuntimeException("未找到该用户的课程预约记录");
               }
          }
          return true;
     }

     @Transactional
     @Override
     public boolean proClassCreate(ProcessDTO processDTO) {
          if (processDTO.isHandle()) {
               Optional<CourseClass> courseClass = classRepository.findById(processDTO.getProcessId());
               if (courseClass.isPresent()) {
                     courseClass.get().setStatus(1);
               }
               else{
                    throw new RuntimeException("查无此课程");
               }
          }
          else{
               classRepository.deleteById(processDTO.getProcessId());
               teachesRepository.deleteByClassID(processDTO.getProcessId());
          }
          return true;
     }

     @Transactional
     @Override
     public boolean proCoach(ProcessDTO processDTO){
          if (processDTO.isHandle()) {
               Optional<Coach> coach =coachRepository.findById(processDTO.getProcessId());
               if (coach.isPresent()) {
                    coach.get().setStatus(1);
               }
               else{
                    throw new RuntimeException("查无此教练");
               }
          }
          else{
               coachRepository.deleteById(processDTO.getProcessId());
          }
          return true;
     }
}
