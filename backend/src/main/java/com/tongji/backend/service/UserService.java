package com.tongji.backend.service;

import com.tongji.backend.entity.*;
import com.tongji.backend.entity.dto.*;
import com.tongji.backend.repository.*;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.security.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository; // 自动注入数据库的操作对象
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    GymRepository gymRepository;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public UserDTO login(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByUserName(loginDTO.getUserName());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //检查传入type与数据库中的type是否一致
            if (!user.getType().equals(loginDTO.getType())) {
                throw new IllegalArgumentException("用户类型错误");
            }
            //将user的属性赋值到UserDTO
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            if (user.getPassword().equals(loginDTO.getPassword())) { // 实际中密码应经过哈希验证
                userDTO.setToken(jwtUtil.generateToken(user.getUserID()));
                return userDTO;
            } else {
                throw new IllegalArgumentException("密码错误");
            }
        } else {
            throw new IllegalArgumentException("用户不存在");
        }
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        User user = new User();
        //检查user表中是否已存在该用户名，若有抛出异常
        if (userRepository.findByUserName(registerDTO.getUserName()).isPresent()) {
            throw new IllegalArgumentException("用户名已存在");
        }
        BeanUtils.copyProperties(registerDTO, user);
        user.setRegistrationTime(LocalDateTime.now());
        User res =userRepository.save(user);
        //需要向教练表/管理员表中插入一条记录
        if (registerDTO.getType().equals("coach")) {
            // 向教练表插入记录
            Coach coach = new Coach();
            coach.setUserID(res.getUserID());
            //BeanUtils.copyProperties(registerDTO, coach);
            coach.setStatus(0);
            coachRepository.save(coach);
        } else if (registerDTO.getType().equals("admin")) {
            //如果传入的gymID为空，代表需要先创建gym
            if (registerDTO.getGymID() == null) {
                // 创建gym
                Gym gym = new Gym();
                gym.setGymName(registerDTO.getGymName());
                gym.setAddress(registerDTO.getAddress());
                gym.setCreateTime(LocalDateTime.now());
                Gym resGym = gymRepository.save(gym);
                registerDTO.setGymID(resGym.getGymID());
           }

            // 向管理员表插入记录
            Admin admin = new Admin();
            admin.setUserID(res.getUserID());
            BeanUtils.copyProperties(registerDTO, admin);
            adminRepository.save(admin);
        }
        return res;
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

    @Override
    public List<Task> getTasks(Integer classID){
        return  taskRepository.findByClassID(classID);
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
