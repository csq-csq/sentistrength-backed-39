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
        System.out.println("find py");
        try (OutputStream outputStream = new FileOutputStream(pythonScriptFile)) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("export py Success!");
        return pythonScriptFile.getAbsolutePath();
    }
    public void runPythonScript(List<List<String>> dateList) throws IOException, InterruptedException {
        System.out.println("get into service!");
        String pythonScriptPath =exportPythonScript("crawler.py");

        // 构造日期字符串
        StringBuilder dateStringBuilder = new StringBuilder();
        for (List<String> dates : dateList) {
            dateStringBuilder.append(dates.get(0)).append(" ").append(dates.get(1)).append(" ");
        }
        String dateString = dateStringBuilder.toString();
        System.out.println(dateString);
        dateString.substring(0,dateString.length()-1);
        System.out.println(dateString);
        String[] command = {"python3 ",pythonScriptPath," --version_dates ",dateString};

        ProcessBuilder processBuilder = new ProcessBuilder();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < command.length; i++){
            sb. append(command[i]);
        }
        String s = sb.toString();
        System.out.println(s.toString());
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("start cmd");
        Process process = processBuilder.start();
        System.out.println("finish cmd");

/*        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }*/

        int exitCode = process.waitFor();
        System.out.println("\nExited with error code : " + exitCode);

    }

}
