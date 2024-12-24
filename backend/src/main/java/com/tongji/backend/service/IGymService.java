package com.tongji.backend.service;

import com.tongji.backend.entity.Gym;
import com.tongji.backend.entity.dto.GymDTO;

import java.util.List;

public interface IGymService {

    Gym createGym(GymDTO gymDTO);

    List<Gym> getAllGym();
}
