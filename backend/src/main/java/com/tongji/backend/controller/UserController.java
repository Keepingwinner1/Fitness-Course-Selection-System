package com.tongji.backend.controller;


import com.tongji.backend.entity.User;
import com.tongji.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/getUserById")
    public String getUserById(int id) {
        User user = userMapper.selectById(id);
        return user!=null?user.getUsername()+"  "+user.getPassword():null;
    }

    @RequestMapping("/createUser")
    public String createUser(String username, String password) {
        int i = userMapper.insert(username, password);
        return i>0?"success":"failed";
    }
}
