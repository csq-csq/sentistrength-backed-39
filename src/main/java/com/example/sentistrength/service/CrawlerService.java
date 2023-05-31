package com.example.sentistrength.service;

import java.io.IOException;
import java.util.List;

public interface CrawlerService {
    public void runPythonScript(List<List<String>> dateList) throws IOException, InterruptedException;

}
