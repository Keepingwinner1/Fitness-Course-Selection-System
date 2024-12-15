package com.tongji.backend.mapper;


import com.tongji.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Insert("insert into user(username,password) VALUES (#{name},#{password})")
    public int insert(@Param("name")String name, @Param("password")String password);

    @Select("SELECT * FROM user WHERE id = #{id}")
    public User selectById(@Param("id")int id);
}
