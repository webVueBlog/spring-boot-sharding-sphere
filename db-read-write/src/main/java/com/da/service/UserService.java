package com.da.service;

import com.da.entity.User;

import java.util.List;

/**
 * @Description: 用户相关接口
 */
public interface UserService {

    /**
     * 获取所有用户信息
     */
    List<User> list();

    /**
     * 单个 保存用户信息
     *
     * @param user
     */
    String saveOne(User user);

}