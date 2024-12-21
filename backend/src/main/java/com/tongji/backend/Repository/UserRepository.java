package com.tongji.backend.Repository;

import com.tongji.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository   //将当前类注册为spring bean
public interface UserRepository extends CrudRepository<User, Integer> {  // 继承CrudRepository接口，自动实现增删改查功能

}
