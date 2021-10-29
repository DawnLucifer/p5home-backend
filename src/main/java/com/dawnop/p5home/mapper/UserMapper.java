package com.dawnop.p5home.mapper;

import com.dawnop.p5home.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from tb_user where username=#{username}")
    User selectByUsername(String username);

}
