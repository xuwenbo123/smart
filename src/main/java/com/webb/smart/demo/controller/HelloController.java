package com.webb.smart.demo.controller;

import com.webb.smart.demo.service.HelloService;
import com.webb.smart.mvc.annotation.*;
import com.webb.smart.util.BeanUtil;

import java.util.Map;

@Controller
public class HelloController {

    @Inject
    private HelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaTypes.TEXT_PLAIN_UTF_8)
    public String hello(String name) {
        return "hello, " + name + "!";
    }

    @RequestMapping(value = "/user/hello", method = RequestMethod.GET, produces = MediaTypes.TEXT_PLAIN_UTF_8)
    public String helloUser(String name, Integer age) {
        return "hello, " + name + "," + age;
    }

    @RequestMapping(value = "/hello/service", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public String helloService(String name) {
        Map<Class<?>, Object> map = BeanUtil.getBeanMap();
        helloService.helloService();
        return name;
    }

}
