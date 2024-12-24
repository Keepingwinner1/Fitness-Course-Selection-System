package com.tongji.backend.controller;


import com.tongji.backend.entity.Gym;
import com.tongji.backend.entity.dto.ResponseMessage;
import com.tongji.backend.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gym")
public class GymController {

    @Autowired
    private GymService gymService;

    @PostMapping("/createGym")
    public ResponseMessage<String> createGym(@RequestBody Gym gym) {
        if(gymService.createGym(gym)){
            return ResponseMessage.success(null);
        }
        else {
            return ResponseMessage.error("创建健身房失败");
        }
    }
}
