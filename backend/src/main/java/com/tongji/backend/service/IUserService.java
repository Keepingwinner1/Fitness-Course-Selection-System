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

    /**
     * 获取用户
     * @param userID
     */
    User get(Integer userID);

    /**
     * 修改用户
     * @param user
     */
    User edit(UserDto user);

    /**
     * 删除用户
     * @param userID
     */
    void delete(Integer userID);
}
