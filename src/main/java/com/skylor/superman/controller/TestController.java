package com.skylor.superman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * @author skylor on 2016/3/8.
 */
@Controller
@RequestMapping("/api/test")
public class TestController {

    @ResponseBody
    @RequestMapping(value = "/show", method= RequestMethod.POST)
    @ApiOperation(value="测试接口", notes="测试接口详细描述")
    public String show(
            @ApiParam(required=true, name="name", value="姓名")
            @RequestParam(name = "name") String stuName){
        return stuName;
    }
}