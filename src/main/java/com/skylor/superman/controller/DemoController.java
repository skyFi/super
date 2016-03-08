package com.skylor.superman.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.skylor.superman.model.User;
import com.wordnik.swagger.annotations.ApiOperation;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @author skylor on 2016/3/8.
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 可以直接使用@ResponseBody响应JSON
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getcount", method = RequestMethod.POST)
    @ApiOperation(value="测试-getCount", notes="getCount更多说明")
    public ModelMap getCount(HttpServletRequest request,
                             HttpServletResponse response) {
        logger.info(">>>>>>>> begin getCount >>>>>>>>");
        ModelMap map = new ModelMap();
        map.addAttribute("count", 158);

        // 后台获取的国际化信息
        map.addAttribute("xstest", "测试");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserNameById", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户姓名", notes = "获取用户姓名")
    public String getUserNameById(
            @RequestParam("id") Long id) {

        String sql = "select username from user where id = ?;";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    /**
     * 可以直接使用@ResponseBody响应JSON
     *
     * @param request
     * @param response
     * @return
     */
    @ApiIgnore//使用该注解忽略这个API
    @ResponseBody
    @RequestMapping(value = "/jsonTest1", method = RequestMethod.POST)
    public ModelMap jsonTest(HttpServletRequest request,
                             HttpServletResponse response) {
        ModelMap map = new ModelMap();
        map.addAttribute("hello", "你好");
        map.addAttribute("veryGood", "很好");

        return map;
    }

    /**
     * 可以直接使用@ResponseBody响应JSON
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest3", method = RequestMethod.POST)
    public List<String> jsonTest3(HttpServletRequest request,
                                  HttpServletResponse response) {
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("你好");
        return list;
    }

    /**
     * JSON请求一个对象<br/>
     * （Ajax Post Data：{"name":"名称","content":"内容"}）
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest2User", method = RequestMethod.POST)
    public ModelMap jsonTest2(@RequestBody User user) {
        logger.info("username：" + user.getUsername());
        logger.info("password：" + user.getPassword());
        ModelMap map = new ModelMap();
        map.addAttribute("username", user.getUsername());
        map.addAttribute("password", user.getPassword());
        return map;
    }

    /**
     * 直接读取URL参数值<br/>
     * /demo/jsonTest6.do?name=Hello&content=World
     *
     * @param demoName
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest6", method = RequestMethod.POST)
    public ModelMap jsonTest6(@RequestParam("name") String demoName, @RequestParam String content) {
        logger.info("demoName：" + demoName);
        ModelMap map = new ModelMap();
        map.addAttribute("name",demoName + "AAA");
        map.addAttribute("content",content + "BBB");
        map.addAttribute("date",new java.util.Date());
        return map;
    }

    /**
     * JSON请求一个对象，将RequestBody自动转换为JSONObject对象<br/>
     * （Ajax Post Data：{"name":"名称","content":"内容"}）
     *
     * 使用JSONObject请添加依赖
     *  <dependency>
     *      <groupId>net.sf.json-lib</groupId>
     *      <artifactId>json-lib</artifactId>
     *      <version>2.4</version>
     *      <!--指定jdk版本 -->
     *      <classifier>jdk15</classifier>
     *  </dependency>
     *
     * @param jsonObject
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest5", method = RequestMethod.POST)
    public ModelMap jsonTest5(@RequestBody JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        logger.info("demoName：" + name);
        ModelMap map = new ModelMap();
        map.addAttribute("demoName",name);
        return map;
    }

    /**
     * 输入 和输出为JSON格式的数据的方式 HttpEntity<?> ResponseEntity<?>
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/jsonTest4", method = RequestMethod.POST)
    public ResponseEntity<String> jsonTest4(HttpEntity<User> user,
                                            HttpServletRequest request, HttpSession session) {
        //获取Headers方法
        HttpHeaders headers = user.getHeaders();

        // 获取内容
        String demoContent = user.getBody().getUsername();

        // 这里直接new一个对象（HttpHeaders headers = new HttpHeaders();）
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("MyHeaderName", "SHANHY");

        ResponseEntity<String> responseResult = new ResponseEntity<String>(
                demoContent, responseHeaders, HttpStatus.OK);
        return responseResult;
    }

}