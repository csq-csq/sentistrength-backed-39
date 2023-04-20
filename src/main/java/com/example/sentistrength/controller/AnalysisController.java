package com.example.sentistrength.controller;

import com.example.sentistrength.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    AnalysisService analysisService;

    @Autowired
    private AnalysisController(AnalysisService analysisService){
        this.analysisService = analysisService;
    }
    @PostMapping("/submit")
    public String initialiseAndRun(@RequestBody Map<String, String> options){
        String[] args = new String[options.size()];
        for(String s: options.values()){
            args[args.length] = s;
        }
        analysisService.initialiseAndRun(args);
        String url = analysisService.getUrl();
        return url;
    }
}
