package com.tongji.backend.service;

import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.LoginDTO;
import com.tongji.backend.entity.dto.ProfileDTO;
import com.tongji.backend.entity.dto.RegisterDTO;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User login(LoginDTO loginDTO);
    User register(RegisterDTO registerDTO);
    ProfileDTO getProfile(Integer userID);
    ProfileDTO editProfile(ProfileDTO profileDTO);


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
