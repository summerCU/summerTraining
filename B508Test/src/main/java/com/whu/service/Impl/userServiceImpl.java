package com.whu.service.Impl;

import com.whu.dao.UserMapper;
import com.whu.entity.user.User;
import com.whu.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private UserMapper  userMapper;

    @Override
    public User Login(String userName, String password) {
        return userMapper.login(userName,password);
    }

    @Override
    public int register(String username, String password) {
        return 0;
    }
}
