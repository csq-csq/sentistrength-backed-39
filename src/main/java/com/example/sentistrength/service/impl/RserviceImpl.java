package com.example.sentistrength.service.impl;

import com.example.sentistrength.service.Rservice;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RserviceImpl implements Rservice {
    public void runRScript() throws IOException, InterruptedException {
        // Step 1: Export the R script
        File rScript = exportResource("/sentistrength.R");

        // Step 2: Build the command
        String rCommand = "/usr/bin/Rscript " + rScript.getAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(rCommand.split(" "));

        // Step 3: Run the command
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        System.out.println("code:"+exitCode);
 /*       ProcessBuilder processBuilder2=new ProcessBuilder("python3","transformtxt.py");
        Process process1=processBuilder2.start();
        int exitCode2=process1.waitFor();
        System.out.println("code2:"+exitCode2);*/
        // Handle the outputs...
    }

    public File exportResource(String resourceName) throws IOException {
        InputStream resourceStream = getClass().getResourceAsStream(resourceName);
        File tempFile = File.createTempFile("temp", null);
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(resourceStream, out);
        }
        return tempFile;
    }

}
