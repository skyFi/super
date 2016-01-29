package com.skylor.superman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by darcy on 2015/12/18.
 */
@Controller
public class SuperViewController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView forwardToView() {
        return new ModelAndView("/index.html");
    }
}
