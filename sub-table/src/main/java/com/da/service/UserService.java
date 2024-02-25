package com.da.service;

import com.da.entity.User;

import java.util.List;

/**
 * @Description: 用户相关接口
 *
 */
public interface UserService {

    /**
     * 获取所有用户信息
     */
    List<User>  list();

    /**
     *   批量 保存用户信息
     * @param userVOList
     */
    String  insertForeach(List<User> userVOList);

}