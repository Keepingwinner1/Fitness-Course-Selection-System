package com.tongji.backend.service;

import com.tongji.backend.entity.Admin;
import com.tongji.backend.entity.Coach;
import com.tongji.backend.entity.Payment;
import com.tongji.backend.entity.dto.LoginDTO;
import com.tongji.backend.entity.dto.ProcessDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAdminService {
    //注册成为管理员
    Admin addAdmin(Admin admin);

    Admin login(LoginDTO loginDTO);

    List<Payment> getAllRefund(Integer gymID);

    List<Coach> getAllApplyCoach(Integer gymID);

    boolean proRefund(ProcessDTO processDTO);

    @Transactional
    boolean proClassCreate(ProcessDTO processDTO);

    @Transactional
    boolean proCoach(ProcessDTO processDTO);
}
