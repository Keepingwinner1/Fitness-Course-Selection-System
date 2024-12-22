package com.tongji.backend.repository;

import com.tongji.backend.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository   //将当前类注册为spring bean
public interface UserRepository extends CrudRepository<User, Integer> {  // 继承CrudRepository接口，自动实现增删改查功能
    Optional<User> findByUserName(String userName);
}
