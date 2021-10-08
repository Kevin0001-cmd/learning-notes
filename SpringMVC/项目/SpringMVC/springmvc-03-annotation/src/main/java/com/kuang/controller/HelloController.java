package com.kuang.controller;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)

public class HelloController {

    //localhost:8080/hello/h1
    @RequestMapping("/h1")
    public String hello(Model model) {
        //封装数据
        model.addAttribute("msg", "Hello,SpringMVCAnnotation");
        return "hello"; //会被试图解析器处理
    }
}
