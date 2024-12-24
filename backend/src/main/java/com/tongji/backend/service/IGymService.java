package com.tongji.backend.service;

import com.tongji.backend.entity.Gym;
import com.tongji.backend.entity.dto.GymDTO;

public interface IGymService {

    Gym createGym(GymDTO gymDTO);
}
