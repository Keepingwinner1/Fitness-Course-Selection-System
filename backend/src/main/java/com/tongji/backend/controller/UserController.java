package com.tongji.backend.controller;


import com.tongji.backend.entity.Task;
import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.security.JwtUtil;
import com.tongji.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //接口对象返回对象，转换成JSON对象
@RequestMapping("/user") //后续访问地址为 url/user/**
public class UserController {



    @Autowired
    IUserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login") // 登录
    public ResponseMessage<UserDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            UserDTO userDTO = userService.login(loginDTO);
            return ResponseMessage.success(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseMessage.error(e.getMessage());
        } catch (Exception e) {
            return ResponseMessage.error("登录失败，请稍后再试");
        }
    }

    @PostMapping("/register") // 注册
    public ResponseMessage<User> register(@RequestBody RegisterDTO registerDTO) {
        User user = userService.register(registerDTO);
        return ResponseMessage.success(user);
    }


    @GetMapping("/profile/{userID}") // 获取用户资料
    public ResponseMessage<ProfileDTO> getUserProfile(@PathVariable Integer userID) {
        ProfileDTO profile = userService.getProfile(userID);
        return ResponseMessage.success(profile);
    }

    @PutMapping("/profile/edit") // 编辑用户资料
    public ResponseMessage<ProfileDTO> editUserProfile(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO profile = userService.editProfile(profileDTO);
        return ResponseMessage.success(profile);
    }

    @GetMapping("/updateToken")
    public ResponseMessage<String> updateToken(@RequestBody String token) {
        return ResponseMessage.success("更新成功",jwtUtil.updateToken(token));
    }

    @PostMapping("/getToken" )
    public ResponseMessage<String> getToken() {
        return ResponseMessage.success("更新成功",jwtUtil.generateToken(1));
    }



//    //增加  //@RequestBody将前台传过来的JSON对象转为User对象
//    @PostMapping
//    public ResponseMessage<User> addUser(@RequestBody UserDto user) {  // URL:  localhost:8080/user
//        //打印接收到的对象
////        System.out.println(user);
//        User userNew =userService.add(user);
//        return ResponseMessage.success(userNew);
//    }
//
//    //查询
//    @GetMapping("/{userID}")
//    public ResponseMessage<User> getUser(@PathVariable Integer userID) {  // URL:  localhost:8080/user/1
//        User user = userService.get(userID);
//        return ResponseMessage.success(user);
//    }
//
//    //修改
//    @PutMapping
//    public ResponseMessage<User> edit(@RequestBody UserDto user) {  // URL:  localhost:8080/user
//        User userNew =userService.edit(user);
//        return ResponseMessage.success(userNew);
//    }
//
//    //删除
//    @DeleteMapping("/{userID}")
//    public ResponseMessage<User> delete(@PathVariable Integer userID) {  // URL:  localhost:8080/user/1
//        userService.delete(userID);
//        return ResponseMessage.success(null);
//    }

}
