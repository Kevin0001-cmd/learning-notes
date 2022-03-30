package com.kuang.dao;

import com.kuang.pojo.Student;
import com.kuang.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {

    //查询所有的老师信息，以及对应的学生的信息
    List<Teacher> getTeachers(@Param("tid")int id);

}
