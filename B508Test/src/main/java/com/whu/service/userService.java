package com.whu.service;

import com.whu.entity.user.User;

public interface  userService {
    /**
     * 用户登录
     * @param userEmail
     * @param password
     * @return
     */
    User Login(String userEmail, String password);
    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    int register(String username,String password);
}
