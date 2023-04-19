package com.example.sentistrength.controller;

import com.example.sentistrength.entity.ReportingOptions;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class ROController {
    @CrossOrigin
    @PostMapping(value = "/api/reportingOptions")
    @ResponseBody
    public Result reportingOptions(@RequestBody ReportingOptions reportingOptions) {
        System.out.println(reportingOptions);
        return ResultFactory.buildSuccessResult("ReportingOptions");
    }
}
