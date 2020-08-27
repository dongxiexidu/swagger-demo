package com.etrans.controller;

import com.etrans.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    //@RequestMapping("/hello")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // 只要我们的接口中,返回值中存在实体类,它就会被扫描到swagger中
    @PostMapping("/user")
    public User user() {
        return new User();
    }

    @ApiOperation("hello控制类")
    @GetMapping("/hello2")
    public String hell2(@ApiParam("用户名") String username) {
        return "hello" + username;
    }

    @ApiOperation("post测试类")
    @PostMapping("/post")
    public User post(@ApiParam("用户名") User user) {
        return user;
    }
}
