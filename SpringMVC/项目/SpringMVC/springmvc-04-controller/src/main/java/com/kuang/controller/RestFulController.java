package com.kuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * RestFul的优点：简洁、高效、安全
 */
@Controller
public class RestFulController {

    //原來的   http://localhost:8080/add?a=1&b=2
    //RestFul   http://localhost:8080/add/a/b
    @RequestMapping(name = "/add/{a}/{b}", method = RequestMethod.DELETE)
    public String test1(@PathVariable int a, @PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果为" + res);
        return "test";
    }
//    @RequestMapping(name = "/add/{a}/{b}", method = RequestMethod.GET)
    @GetMapping("/add/{a}/{b}")
    public String test2(@PathVariable int a, @PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果为" + res);
        return "test";
    }
//    @RequestMapping(name = "/add/{a}/{b}", method = RequestMethod.POST)
    @PostMapping("/add/{a}/{b}")
    public String test3(@PathVariable int a, @PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果为" + res);
        return "test";
    }
}
