package com.tongji.backend.service;

import com.tongji.backend.entity.Task;
import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    UserDTO login(LoginDTO loginDTO);
    User register(RegisterDTO registerDTO);
    ProfileDTO getProfile(Integer userID);
    ProfileDTO editProfile(ProfileDTO profileDTO);

    List<Task> getTasks(Integer classID);


//    /**
//     * 添加用户
//     * @param user
//     */
//    User add(UserDto user);
//
//    /**
//     * 获取用户
//     * @param userID
//     */
//    User get(Integer userID);
//
//    /**
//     * 修改用户
//     * @param user
//     */
//    User edit(UserDto user);
//
//    /**
//     * 删除用户
//     * @param userID
//     */
//    void delete(Integer userID);
}
