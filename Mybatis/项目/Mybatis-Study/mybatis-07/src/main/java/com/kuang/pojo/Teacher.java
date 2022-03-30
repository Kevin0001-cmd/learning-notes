package com.kuang.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Teacher {

    private int id;
    private String name;
    private List<Student> students;   //一个老师对应多个学生，因此用List，否则查出来的结果只能显示一个学生
}
