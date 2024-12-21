package com.tongji.backend.service;

import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.UserDto;
import com.tongji.backend.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository; // 自动注入数据库的操作对象

    @Override
    public User add(UserDto user) {

        User userEntity = new User();
        BeanUtils.copyProperties(user,userEntity); // 将userDto中的属性值拷贝到userEntity中

        return userRepository.save(userEntity);  //增加与修改均可以可调用save方法，自动判断传入的user类的ID有则为修改，无则为新增
         //这个方法会返回当前操作的对象

    }
}
