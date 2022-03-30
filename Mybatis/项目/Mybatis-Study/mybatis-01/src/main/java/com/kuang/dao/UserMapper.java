package com.kuang.dao;

import com.kuang.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<User> getUserList();

    User selectUser(int id);

    int insertUser(User user);
    //万能的map
    int addUser2(Map<String,Object> map);

    int deleteUser(int id);

    int updateUser(User user);
}
