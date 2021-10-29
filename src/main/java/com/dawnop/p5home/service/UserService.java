package com.dawnop.p5home.service;

import com.dawnop.p5home.entity.User;
import com.dawnop.p5home.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public User queryUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}
