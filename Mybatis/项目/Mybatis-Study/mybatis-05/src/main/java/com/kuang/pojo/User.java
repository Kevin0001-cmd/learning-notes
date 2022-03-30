package com.kuang.pojo;

//@Alias("hello")   //使用@Alias时，需要设置别名的模式在扫描包的模式下

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;


}
