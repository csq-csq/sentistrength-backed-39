package com.example.sentistrength.service.impl;

import com.example.sentistrength.enums.SentiStrengthManager;
import com.example.sentistrength.service.AnalysisService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private SentiStrength sentiStrength;
    private PathServiceImpl pathServiceImpl;

    private String url;


    @Autowired
    private AnalysisServiceImpl(){
        sentiStrength = new SentiStrength();
        pathServiceImpl = new PathServiceImpl();
    }

    public void initialiseAndRun(String[] args){
        sentiStrength.initialiseAndRun(args);
        File outputFile = new File(pathServiceImpl.getDownloadPath());
        url = pathServiceImpl.getResultPath() + "result.zip";
        File[] fs = outputFile.listFiles();
        compress(fs, url, false);
    }

    public String getUrl(){
        return url;
    }

    private static void compress(File[] fs, String zipFilePath, Boolean keepDirStructure) {
        byte[] buf = new byte[1024];
        File zipFile = new File(zipFilePath);
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for (File f: fs) {
                String relativePath = f.getPath();
                String relativeName = f.getName();
                if (StringUtils.isEmpty(relativePath)) {
                    continue;
                }
                File sourceFile = new File(relativePath);
                if (sourceFile == null || !sourceFile.exists()) {
                    continue;
                }
                Scanner sc = new Scanner(sourceFile);
                while(sc.hasNext()){
                    System.out.println(sc.nextLine());
                }
                FileInputStream fis = new FileInputStream(sourceFile);
                if (keepDirStructure != null && keepDirStructure) {
                    System.out.println(relativePath);
                    zos.putNextEntry(new ZipEntry(relativePath));
                } else {
                    zos.putNextEntry(new ZipEntry(relativeName));
                }
                int len;
                while ((len = fis.read(buf)) > 0) {
                    zos.write(buf, 0, len);
                }
                //System.out.println(zipFile.listFiles()[0]);
                zos.closeEntry();
            }
            //System.out.println(zipFile.listFiles()[0]);
            zos.close();
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
