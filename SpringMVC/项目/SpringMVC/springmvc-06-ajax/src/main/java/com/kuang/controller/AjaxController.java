package com.kuang.controller;

import com.alibaba.fastjson.JSON;
import com.kuang.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {

    @RequestMapping("/a1")
    public void ajax1(String name, HttpServletResponse response) throws IOException {
        if ("admin".equals(name)) {
            response.getWriter().print("true");
        } else {
            response.getWriter().print("false");
        }
    }


    @RequestMapping("/a2")
    public List<User> ajax2() {
        List<User> list = new ArrayList<User>();
        list.add(new User("秦疆1号", 3, "男"));
        list.add(new User("秦疆2号", 3, "男"));
        list.add(new User("秦疆3号", 3, "男"));
//        String s = JSON.toJSONString(list);
//        System.out.println("===========s=========="+s);
        return list; //由于@RestController注解，将list转成json格式返回
    }

}
