package com.tongji.backend.service;


import com.tongji.backend.entity.Gym;
import com.tongji.backend.entity.dto.GymDTO;
import com.tongji.backend.repository.GymRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GymService implements IGymService {
    @Autowired
    private GymRepository gymRepository;

    @Override
    public Gym createGym(GymDTO gymDTO) {
        Gym gym=new Gym();
        BeanUtils.copyProperties(gymDTO,gym);
        gym.setCreateTime(LocalDateTime.now());
        return gymRepository.save(gym);
    }
}
