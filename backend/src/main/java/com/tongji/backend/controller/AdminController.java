package com.tongji.backend.controller;


import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.service.AdminService;
import com.tongji.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CourseService courseService;

    //注册成为某健身房的管理员
    @GetMapping("/register/{userID}/{gymID}")
    public ResponseMessage<Admin> register(@PathVariable int userID, @PathVariable int gymID) {
        return ResponseMessage.success(adminService.addAdmin(new Admin(userID,gymID)));
    }


    @GetMapping("/login")
    public ResponseMessage<Admin> login(@RequestBody LoginDTO loginDTO) {
        try{
            Admin admin=adminService.login(loginDTO);
            return ResponseMessage.success(admin);
        }
        catch(Exception e){
            return ResponseMessage.error(e.getMessage());
        }
    }

    @GetMapping("/getAllRefund/{gymID}")
    public ResponseMessage<List<Payment>> getAllRefund(@PathVariable int gymID) {
        try{
            List<Payment> refunds=adminService.getAllRefund(gymID);
            return ResponseMessage.success(refunds);
        }
        catch(Exception e){
            return ResponseMessage.error(e.getMessage());
        }
    }

    @GetMapping("/getAllClassCreate/{gymID}")
    public ResponseMessage<List<ClassDTO>> getAllClassCreate(@PathVariable int gymID) {
        try{
              List<ClassDTO> classDTOS=courseService.getAllcreateCLass(gymID);
              return ResponseMessage.success(classDTOS);
        }
        catch(Exception e){
            return ResponseMessage.error(e.getMessage());
        }
    }

    @GetMapping("/getAllApplyCoach/{gymID}")
    public  ResponseMessage<List<Coach>> getAllApplyCoach(@PathVariable int gymID) {
        try{
            List<Coach> coachs=adminService.getAllApplyCoach(gymID);
            return ResponseMessage.success(coachs);
        } catch (Exception e) {
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PutMapping("/proRefund")
    public ResponseMessage<String> proRefund(@RequestBody ProcessDTO processDTO) {
        try {
            adminService.proRefund(processDTO);
            return ResponseMessage.successmsg("退款处理成功");
        }
        catch(Exception e){
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PutMapping("/proClassCreate")
    public ResponseMessage<String> proClassCreate(@RequestBody ProcessDTO processDTO) {
        try{
            adminService.proClassCreate(processDTO);
            return ResponseMessage.successmsg("课程审核处理成功");
        }
        catch (Exception e){
            return ResponseMessage.error(e.getMessage());
        }
    }

    @PutMapping("/proCoach")
    public ResponseMessage<String> proCoach(@RequestBody ProcessDTO processDTO) {
        try{
            adminService.proCoach(processDTO);
            return ResponseMessage.successmsg("教练审核处理成功");
        }
        catch (Exception e){
            return ResponseMessage.error(e.getMessage());
        }
    }
    
}
