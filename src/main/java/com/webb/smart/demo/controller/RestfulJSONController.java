package com.webb.smart.demo.controller;

import com.webb.smart.entity.User;
import com.webb.smart.mvc.annotation.Controller;
import com.webb.smart.mvc.annotation.MediaTypes;
import com.webb.smart.mvc.annotation.RequestMapping;
import com.webb.smart.mvc.annotation.RequestMethod;

@Controller
public class RestfulJSONController {

    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public User json(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(24);
        return user;
    }

    @RequestMapping(value = "/postJson", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    public String postJson(String name) {
        System.out.println(name);
        return "postJson";
    }
}
