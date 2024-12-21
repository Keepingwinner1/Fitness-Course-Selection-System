package com.tongji.backend.entity.dto;

import java.time.LocalDateTime;

public class RegisterDTO {
    private String userName;
    private String password;
    private String type; //user coach admin
    private LocalDateTime registrationTime;
}
