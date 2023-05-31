package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RscriptController {
    @PostMapping("/pretreatment")
    public Result runRscript(@RequestBody String reportingOptions){
        try {
            return new ResultFactory().buildSuccessResult("yes");
        }catch(Exception e){
            return new ResultFactory().buildFailResult("爬取失败");
        }


    }

}
