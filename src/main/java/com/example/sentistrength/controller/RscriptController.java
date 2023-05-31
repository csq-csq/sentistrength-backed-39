package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.Rservice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RscriptController {
    private Rservice rservice;
    @PostMapping("/pretreatment")
    public Result runRscript(@RequestBody String reportingOptions){

        System.out.println("Rcontroller achieve");
        try {
            System.out.println("Rservice achieve");
            rservice.runRScript();
            return new ResultFactory().buildSuccessResult("yes");
        }catch(Exception e){
            return new ResultFactory().buildFailResult("爬取失败");
        }


    }

}
