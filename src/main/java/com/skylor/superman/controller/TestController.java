package com.skylor.superman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author skylor on 2016/3/8.
 */
@Controller
@RequestMapping("/api/test")
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/show", method= RequestMethod.POST)
    public String show(
            @RequestParam(name = "name") String stuName){
        return "hello " + stuName + ", nice to meet you!";
    }

    @ResponseBody
    @RequestMapping(value = "/show", method= RequestMethod.GET)
    public String getName(){
        return "hello boy, nice to meet you!";
    }
}