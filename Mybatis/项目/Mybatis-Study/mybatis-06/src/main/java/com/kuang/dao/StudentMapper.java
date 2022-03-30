package com.kuang.dao;

import com.kuang.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {

//    @Select(value = "select * from student where id = #{tid}")
//    List<Student> selectStudent(@Param("tid") int id);

    //查询所有的学生信息，以及对应的老师的信息
    List<Student> getStudents();


    List<Student> getStudents2();
}
