package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.AnalysisService;
import com.example.sentistrength.service.PathService;
import com.example.sentistrength.service.impl.PathServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AnalysisController {
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private PathService pathService;

    private ArrayList<String> args;
    private ArrayList<String> optionsArgs;
    private ArrayList<String> sentiStrengthArgs;

    @Autowired
    private AnalysisController(AnalysisService analysisService, PathService pathService){
        this.analysisService = analysisService;
        this.pathService = pathService;
        args = new ArrayList<>();
        sentiStrengthArgs = new ArrayList<>();
        optionsArgs = new ArrayList<>();
    }

    @PostMapping("/submit")
    public Result initialiseAndRun(){
        for(String s : sentiStrengthArgs){
            args.add(s);
        }
        for(String s : optionsArgs){
            args.add(s);
        }
        args.add("uploader");
        args.add(pathService.getUploadPath());
        args.add("downloader");
        args.add(pathService.getDownloadPath());
        pathService.deleteFiles(pathService.getDownloadPath());
        analysisService.initialiseAndRun((String[]) args.toArray(new String[args.size()]));
        String url = analysisService.getUrl();
        args.clear();
        return ResultFactory.buildSuccessResult(url);
    }
    @PostMapping("/options")
    public void getOptions(@RequestBody Map<String, String> options){
        optionsArgs.clear();
        for(String s : options.values()){
            if(s != null && s.length() != 0){
                optionsArgs.add(s);
            }
        }
    }

    @PostMapping("/sentiArgs")
    public void getSentiArgs(@RequestBody Map<String, String> sentiArgs){
        sentiStrengthArgs.clear();
        for(String s : sentiArgs.values()){
            if(s != null && s.length() != 0) {
                sentiStrengthArgs.add(s);
            }
        }
    }
}
