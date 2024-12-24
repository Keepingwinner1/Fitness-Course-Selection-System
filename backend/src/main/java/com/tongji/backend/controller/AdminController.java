package com.tongji.backend.controller;


import com.tongji.backend.entity.Admin;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //注册成为某健身房的管理员
    @PostMapping("/register/{userID}/{gymID}")
    public ResponseMessage<String> register(@PathVariable int userID, @PathVariable int gymID) {
        if (adminService.addAdmin(new Admin(userID, gymID))) {
            return ResponseMessage.success(null);
        }
        else {
            return ResponseMessage.error("管理员注册失败");
        }
    }


}
