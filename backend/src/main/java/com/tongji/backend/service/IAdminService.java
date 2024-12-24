package com.tongji.backend.service;

import com.tongji.backend.entity.Admin;

public interface IAdminService {
    //注册成为管理员
    boolean addAdmin(Admin admin);
}
