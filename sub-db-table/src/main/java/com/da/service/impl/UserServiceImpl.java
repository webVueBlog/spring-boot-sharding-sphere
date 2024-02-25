package com.da.service.impl;

import com.da.entity.User;
import com.da.mapper.UserMapper;
import com.da.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @Description: 用户实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public  List<User> list() {
        List<User> users = userMapper.selectAll();//查询所有用户
        return users;//返回用户列表
    }

    @Override
    public String insertForeach(List<User> userList) {//批量插入数据
        //设置创建时间和更新时间
        for (User user : userList) {
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setStatus(0);
        }
        //批量插入数据
        userMapper.insertForeach(userList);
        return "保存成功";
    }
}
