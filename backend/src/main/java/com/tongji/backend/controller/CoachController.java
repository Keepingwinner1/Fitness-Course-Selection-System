package com.tongji.backend.controller;


import com.tongji.backend.entity.Coach;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @PostMapping("/register")
    public ResponseMessage<String> register(Coach coach) {
        if(coachService.addCoach(coach)) {
            return ResponseMessage.success(null);
        }
        else{
            return ResponseMessage.error("教练注册失败");
        }
    }

}
