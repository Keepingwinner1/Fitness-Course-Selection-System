package com.tongji.backend.service;

import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.UserDto;
import com.tongji.backend.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository; // 自动注入数据库的操作对象

    @Override
    public User add(UserDto user) {
//        System.out.println("传入: "+user);
        User userEntity = new User();
        BeanUtils.copyProperties(user,userEntity); // 将userDto中的属性值拷贝到userEntity中
//        System.out.println("转换后： "+userEntity.getPassword());
        // 打印 UserDto 和 User 实体的内容，用于调试
//        logger.debug("UserDto: {}", user.toString());
//        logger.debug("UserEntity before save: {}", userEntity);

        return userRepository.save(userEntity);  //增加与修改均可以可调用save方法，自动判断传入的user类的ID有则为修改，无则为新增
         //这个方法会返回当前操作的对象
    }

    @Override
    public User get(Integer userID) {
        return userRepository.findById(userID).orElseThrow(()->{
            throw new IllegalArgumentException("用户不存在或参数异常");
        });
    }

    @Override
    public User edit(UserDto user) {
        User userEntity = new User();
        BeanUtils.copyProperties(user,userEntity); // 将userDto中的属性值拷贝到userEntity中
        return userRepository.save(userEntity);
    }

    @Override
    public void delete(Integer userID) {
        userRepository.deleteById(userID);
    }


}
