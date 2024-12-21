package com.tongji.backend.service;

import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    /**
     * 添加用户
     * @param user
     */
    User add(UserDto user);

}
