package com.example.sentistrength.controller;

import com.example.sentistrength.entity.DateListRequest;
import com.example.sentistrength.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PythonScriptController {
    @Autowired
    private CrawlerService crawlerService;

    @PostMapping("/runPythonScript")
    public ResponseEntity<?> runPythonScript(@RequestBody DateListRequest request) {
        try {
            crawlerService.runPythonScript(request.getDateList());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
