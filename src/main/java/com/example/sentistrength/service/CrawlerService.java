package com.example.sentistrength.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CrawlerService {
    public void runPythonScript(List<List<String>> dateList) throws IOException, InterruptedException;

}
