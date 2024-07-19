package com.example.SecretManagerTest.mapper;

import com.example.SecretManagerTest.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM public.users WHERE id = #{id}")
    List<User> findById(String id);
}