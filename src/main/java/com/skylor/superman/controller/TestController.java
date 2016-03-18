package com.skylor.superman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author skylor on 2016/3/8.
 */
@Controller
@RequestMapping("/api/test")
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public String show(
            @RequestParam(name = "name") String stuName) {
        return "hello " + stuName + ", nice to meet you!";
    }

    @RequestMapping(value = "/getName", method = RequestMethod.GET)
    public String getName() {
        return "hello boy, nice to meet you!";
    }

    @RequestMapping(value = "/getCustomName", method = RequestMethod.GET)
    public String getName(@RequestParam(name = "name") String stuName) {
        return "hello " + stuName + " nice to meet you! 天王盖地虎";
    }

    @RequestMapping(value = "/getImgUrl", method = RequestMethod.GET)
    public ModelAndView getImgUrl(@RequestParam(name = "num") String imgNum) {
        return new ModelAndView("redirect:/lib/img/" + imgNum + ".jpg");
    }

}