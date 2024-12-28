package com.tongji.backend.service;

import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.LoginDTO;
import com.tongji.backend.entity.dto.ProfileDTO;
import com.tongji.backend.entity.dto.RegisterDTO;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository; // 自动注入数据库的操作对象

    @Override
    public ResponseMessage<User> login(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByUserName(loginDTO.getUserName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginDTO.getPassword())) { // 实际中密码应经过哈希验证
                return  ResponseMessage.success(user);
            } else {
                return  ResponseMessage.error("密码错误");
            }
        } else {
            return  ResponseMessage.error("用户不存在");
        }
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        user.setRegistrationTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public ProfileDTO getProfile(Integer userID) {
        User user = userRepository.findById(userID).orElseThrow(() -> {
            throw new IllegalArgumentException("用户不存在");
        });
        ProfileDTO profileDTO = new ProfileDTO();
        BeanUtils.copyProperties(user, profileDTO);
        return profileDTO;
    }

    @Override
    public ProfileDTO editProfile(ProfileDTO profileDTO) {
        // 从数据库中查找用户
        User user = userRepository.findById(profileDTO.getUserID()).orElseThrow(() -> {
            throw new IllegalArgumentException("用户不存在");
        });

        // 更新非空字段到数据库
        if (profileDTO.getAge() != null) {
            user.setAge(profileDTO.getAge());
        }
        if (profileDTO.getEmail() != null && !profileDTO.getEmail().isEmpty()) {
            user.setEmail(profileDTO.getEmail());
        }
        if (profileDTO.getGender() != null && !profileDTO.getGender().isEmpty()) {
            user.setGender(profileDTO.getGender());
        }
        if (profileDTO.getIconURL() != null && !profileDTO.getIconURL().isEmpty()) {
            user.setIconURL(profileDTO.getIconURL());
        }
        if (profileDTO.getTags() != null && !profileDTO.getTags().isEmpty()) {
            user.setTags(profileDTO.getTags());
        }
        if (profileDTO.getIntroduction() != null && !profileDTO.getIntroduction().isEmpty()) {
            user.setIntroduction(profileDTO.getIntroduction());
        }
        if (profileDTO.getGoalType() != null && !profileDTO.getGoalType().isEmpty()) {
            user.setGoalType(profileDTO.getGoalType());
        }
        if (profileDTO.getGoalWeight() != null) {
            user.setGoalWeight(profileDTO.getGoalWeight());
        }

        // 保存更新后的用户数据
        userRepository.save(user);

        // 将更新后的实体转为 ProfileDTO 返回
        ProfileDTO updatedProfileDTO = new ProfileDTO();
        BeanUtils.copyProperties(user, updatedProfileDTO);
        return updatedProfileDTO;
    }


//    @Override
//    public User add(UserDto user) {
//        User userEntity = new User();
//        BeanUtils.copyProperties(user,userEntity); // 将userDto中的属性值拷贝到userEntity中
//        return userRepository.save(userEntity);  //增加与修改均可以可调用save方法，自动判断传入的user类的ID有则为修改，无则为新增
//         //这个方法会返回当前操作的对象
//    }
//
//    @Override
//    public User get(Integer userID) {
//        return userRepository.findById(userID).orElseThrow(()->{
//            throw new IllegalArgumentException("用户不存在或参数异常");
//        });
//    }
//
//    @Override
//    public User edit(UserDto user) {
//        User userEntity = new User();
//        BeanUtils.copyProperties(user,userEntity); // 将userDto中的属性值拷贝到userEntity中
//        return userRepository.save(userEntity);
//    }
//
//    @Override
//    public void delete(Integer userID) {
//        userRepository.deleteById(userID);
//    }


}
