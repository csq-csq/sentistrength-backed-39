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
     public Result getPoint(@RequestBody String sth) {
         System.out.println("Rcontroller achieve");
         try {
             System.out.println("Rservice achieve");
             pointService.getpoint();
             return new ResultFactory().buildSuccessResult("yes");
         }catch(Exception e){
             return new ResultFactory().buildFailResult("爬取失败");
         }

     }
}
