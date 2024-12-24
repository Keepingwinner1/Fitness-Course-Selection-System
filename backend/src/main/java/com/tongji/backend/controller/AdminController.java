package com.tongji.backend.controller;


import com.tongji.backend.entity.Admin;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //注册成为某健身房的管理员
    @GetMapping("/register/{userID}/{gymID}")
    public ResponseMessage<Admin> register(@PathVariable int userID, @PathVariable int gymID) {
        return ResponseMessage.success(adminService.addAdmin(new Admin(userID,gymID)));
    }



}
