package com.tongji.backend.service;


import com.tongji.backend.entity.Admin;
import com.tongji.backend.repository.AdminRepository;
import com.tongji.backend.repository.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {

     @Autowired
     AdminRepository adminRepository;
     @Autowired
     GymRepository gymRepository;

     @Override
     public boolean addAdmin(Admin admin) {
         try {
             adminRepository.save(admin);
             return true;
         }
         catch (Exception e) {
             return false;
         }
     }
}
