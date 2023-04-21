package com.example.sentistrength.controller;

import com.example.sentistrength.service.AnalysisService;
import com.example.sentistrength.service.PathService;
import com.example.sentistrength.service.impl.PathServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalysisController {
    private AnalysisService analysisService;
    private PathService pathService;

    private ArrayList<String> args = new ArrayList<>();

    @Autowired
    private AnalysisController(AnalysisService analysisService, PathService pathService){
        this.analysisService = analysisService;
        this.pathService = pathService;
        args = new ArrayList<>();
    }

    @PostMapping("/submit")
    public String initialiseAndRun(){
        args.add("upload");
        args.add(pathService.getUploadPath());
        args.add("download");
        args.add(pathService.getDownloadPath());
        analysisService.initialiseAndRun((String[]) args.toArray(new String[args.size()]));
        String url = analysisService.getUrl();
        args.clear();
        return url;
    }
    @PostMapping("/options")
    public void getOptions(@RequestBody Map<String, String> options){
        for(String s : options.values()){
            if(s != null && s.length() != 0){
                args.add(s);
            }
        }
    }

    @PostMapping("/sentiArgs")
    public void getSentiArgs(@RequestBody Map<String, String> sentiArgs){
        for(String s : sentiArgs.values()){
            if(s != null && s.length() != 0) {
                args.add(s);
            }
        }
    }
}
