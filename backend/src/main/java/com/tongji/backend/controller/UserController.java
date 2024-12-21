package com.tongji.backend.controller;


import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.entity.dto.UserDto;
import com.tongji.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //接口对象返回对象，转换成JSON对象
@RequestMapping("/user") //后续访问地址为 url/user/**
public class UserController {


    @Autowired
    IUserService userService;
    //REST
    //增加
    @PostMapping
    public ResponseMessage<User> addUser(@RequestBody UserDto user) {  //@RequestBody将前台传过来的JSON对象转为User对象
        User userNew =userService.add(user);
        return ResponseMessage.success(userNew);
    }

//    @Autowired
//    UserMapper userMapper;
//
//    @RequestMapping("/getUserById")
//    public String getUserById(int id) {
//        User user = userMapper.selectById(id);
//        return user!=null?user.getUsername()+"  "+user.getPassword():null;
//    }
//
//    @RequestMapping("/createUser")
//    public String createUser(String username, String password) {
//        int i = userMapper.insert(username, password);
//        return i>0?"success":"failed";
//    }
}
