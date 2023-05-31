package com.example.sentistrength.service.impl;

import com.example.sentistrength.service.CrawlerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CrawlerServiceImpl implements CrawlerService {
    public void runPythonScript(List<List<String>> dateList) throws IOException, InterruptedException {
        String pythonScriptPath = "";

        // 构造日期字符串
        StringBuilder dateStringBuilder = new StringBuilder();
        for (List<String> dates : dateList) {
            dateStringBuilder.append(dates.get(0)).append(",").append(dates.get(1)).append(";");
        }
        String dateString = dateStringBuilder.toString();

        String[] command = {"python", pythonScriptPath, "--dates", dateString};

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

    }

}
