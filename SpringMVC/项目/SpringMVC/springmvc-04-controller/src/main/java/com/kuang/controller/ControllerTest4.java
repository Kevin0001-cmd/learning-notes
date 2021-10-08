package com.kuang.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerTest4 {


    @RequestMapping("/m1/t1")
    public String test1(Model model){
        model.addAttribute("msg","hello Zkf");
        return "test";
    }
}
