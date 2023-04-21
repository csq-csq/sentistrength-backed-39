package com.example.sentistrength.controller;

import com.example.sentistrength.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalysisController {
    private AnalysisService analysisService;

    private ArrayList<String> args;

    @Autowired
    private AnalysisController(AnalysisService analysisService){
        this.analysisService = analysisService;
        args = new ArrayList<>();
    }
    @PostMapping("/submit")
    public String initialiseAndRun(){
        analysisService.initialiseAndRun((String[]) args.toArray(new String[args.size()]));
        String url = analysisService.getUrl();
        args.clear();
        return url;
    }
    @PostMapping("/options")
    public void getOptions(@RequestBody Map<String, String> options){
        for(String s : options.values()){
            args.add(s);
        }
    }

    @PostMapping("/sentiArgs")
    public void getSentiArgs(@RequestBody Map<String, String> sentiArgs){
        for(String s : sentiArgs.values()){
            args.add(s);
        }
    }
}
