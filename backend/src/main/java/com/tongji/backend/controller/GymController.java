package com.tongji.backend.controller;


import com.tongji.backend.entity.Gym;
import com.tongji.backend.entity.dto.GymDTO;
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
    public ResponseMessage<Gym> createGym(@RequestBody GymDTO gym) {
            return ResponseMessage.success(gymService.createGym(gym));
    }


}
