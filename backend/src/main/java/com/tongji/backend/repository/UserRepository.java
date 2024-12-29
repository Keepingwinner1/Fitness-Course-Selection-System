package com.tongji.backend.repository;

import com.tongji.backend.entity.User;
import com.tongji.backend.entity.dto.StuDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository   //将当前类注册为spring bean
public interface UserRepository extends CrudRepository<User, Integer> {  // 继承CrudRepository接口，自动实现增删改查功能
    Optional<User> findByUserName(String userName);

    @Query("select u from User u join " +
            "Advise a on u.userID=a.userId where a.classId=?1")
    List<User> getClassSTU(Integer classId);
}
