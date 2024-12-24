package com.tongji.backend.service;


import com.tongji.backend.entity.Coach;
import com.tongji.backend.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoachService implements ICoachService {

    @Autowired
    private CoachRepository coachRepository;

    @Override
    public boolean addCoach(Coach coach) {
        try {
            coachRepository.save(coach);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
