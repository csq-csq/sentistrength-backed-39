package com.example.sentistrength.controller;

import com.example.sentistrength.entity.DatesWrapper;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/api")
public class PointController {
    @Autowired
     private PointService pointService;
     @PostMapping("analysis")
     public Result getPoint(@RequestBody String sth) {
         System.out.println("Pointcontroller achieve");
         try {
             System.out.println("Pointservice achieve");
             pointService.getpoint();
             return new ResultFactory().buildSuccessResult("yes");
         }catch(Exception e){
             return new ResultFactory().buildFailResult("失败");
         }

     }
}
