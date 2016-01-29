package com.skylor.superman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by darcy on 2016/1/29.
 */
@Controller
public class RestController {

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String downFile() {
        System.out.println("down file");
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/index.html?login=true";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginToIndex() {
        return "redirect:/index.html?login=true";
    }
}
