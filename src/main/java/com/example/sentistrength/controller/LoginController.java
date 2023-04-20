package com.example.sentistrength.controller;

import com.example.sentistrength.entity.User;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@RestController
public class LoginController {
    @CrossOrigin
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        if (!Objects.equals("admin", username) || !Objects.equals("123456", requestUser.getPassword())) {
            String message = "账号密码错误";
            System.out.println("test");
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(username);
        }
    }

}
