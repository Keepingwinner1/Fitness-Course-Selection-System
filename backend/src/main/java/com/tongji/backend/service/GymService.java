package com.tongji.backend.service;


import com.tongji.backend.entity.Gym;
import com.tongji.backend.repository.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymService implements IGymService {
    @Autowired
    private GymRepository gymRepository;

    @Override
    public boolean createGym(Gym gym) {
        try {
            gymRepository.save(gym);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
