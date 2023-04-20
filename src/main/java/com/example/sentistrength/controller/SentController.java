package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SentController {

    @GetMapping(value = "/api")
    public Result sent(){
        return ResultFactory.buildSuccessResult("index");
    }
}
