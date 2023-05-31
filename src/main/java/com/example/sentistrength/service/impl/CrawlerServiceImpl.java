package com.example.sentistrength.service.impl;

import com.example.sentistrength.service.CrawlerService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
@Service
public class CrawlerServiceImpl implements CrawlerService {

    public String exportPythonScript(String scriptName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(scriptName);
        InputStream inputStream = classPathResource.getInputStream();
        File pythonScriptFile = File.createTempFile("crawler", ".py");
        try (OutputStream outputStream = new FileOutputStream(pythonScriptFile)) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return pythonScriptFile.getAbsolutePath();
    }
    public void runPythonScript(List<List<String>> dateList) throws IOException, InterruptedException {
        String pythonScriptPath =exportPythonScript("crawler.py");

        // 构造日期字符串
        StringBuilder dateStringBuilder = new StringBuilder();
        for (List<String> dates : dateList) {
            dateStringBuilder.append(dates.get(0)).append(" ").append(dates.get(1)).append(" ");
        }
        String dateString = dateStringBuilder.toString();
        //dateString.substring(0,dateString.length()-2);
        String[] command = {"python", pythonScriptPath, "--version_dates", dateString};

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
