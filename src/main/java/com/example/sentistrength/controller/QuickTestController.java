package com.example.sentistrength.controller;

import com.example.sentistrength.entity.ShortText;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class QuickTestController {
    @CrossOrigin
    @PostMapping(value = "/api/quickTest")
    @ResponseBody
    public Result reportingOptions(@RequestBody ShortText shortText) {
        System.out.println(shortText);
        return ResultFactory.buildSuccessResult("QuickTest");
    }
}
