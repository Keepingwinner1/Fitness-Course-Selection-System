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
    //增加  //@RequestBody将前台传过来的JSON对象转为User对象
    @PostMapping
    public ResponseMessage<User> addUser(@RequestBody UserDto user) {  // URL:  localhost:8080/user
        //打印接收到的对象
//        System.out.println(user);
        User userNew =userService.add(user);
        return ResponseMessage.success(userNew);
    }

    //查询
    @GetMapping("/{userID}")
    public ResponseMessage<User> getUser(@PathVariable Integer userID) {  // URL:  localhost:8080/user/1
        User user = userService.get(userID);
        return ResponseMessage.success(user);
    }

    //修改
    @PutMapping
    public ResponseMessage<User> edit(@RequestBody UserDto user) {  // URL:  localhost:8080/user
        User userNew =userService.edit(user);
        return ResponseMessage.success(userNew);
    }

    //删除
    @DeleteMapping("/{userID}")
    public ResponseMessage<User> delete(@PathVariable Integer userID) {  // URL:  localhost:8080/user/1
        userService.delete(userID);
        return ResponseMessage.success(null);
    }

}
