package com.kuang.dao;

import com.kuang.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User selectUser(int id);

    /*分页*/
    List<User> getUserListByLimit(Map<String, Integer> map);

    /*分页*/
    List<User> getUserListByRowBounds(String s, Object o, RowBounds rowBounds);

//    @Select("select * from user")
//    List<User> getUser();
//
//    @Select("select * from user where id = #{id}")
//    User getUserById(@Param("id")int id);
//
//    @Insert("insert into user(id,name,pwd) values(#{id},#{name},#{pwd})")
//    int insertUser(User user);
//
//    @Update("update user set name=#{name},pwd=#{pwd} where id = #{id}")
//    int updateUser(User user);
//
//    @Delete("delete from user where id = #{uid}")
//    int deleteUser(@Param("uid") int id);



}
