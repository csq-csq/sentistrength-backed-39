package com.example.sentistrength.service.impl;

import com.example.sentistrength.service.PathService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PathServiceImpl implements PathService {
    @Value("${files.upload.windowsPath}")
    private String windowsUploadPath;

    @Value("${files.download.windowsPath}")
    private String windowsDownloadPath;

    @Value("${files.upload.linuxPath}")
    private String linuxUploadPath;

    @Value("${files.download.linuxPath}")
    private String linuxDownloadPath;

    @Value("${files.result.windowsPath}")
    private String windowsResultPath;

    @Value("${files.result.linuxPath}")
    private String linuxResultPath;

    private String getOsName(){
        return System.getProperty("os.name");
    }
    public String getUploadPath(){
        if(getOsName().charAt(0) == 'W'){
            return windowsUploadPath;
        }
        else{
            return linuxUploadPath;
        }
    }

    public String getDownloadPath(){
        if(getOsName().charAt(0) == 'W'){
            return windowsDownloadPath;
        }
        else {
            return linuxDownloadPath;
        }
    }

    public String getResultPath(){
        if(getOsName().charAt(0) == 'W'){
            return windowsResultPath;
        }
        else {
            return linuxResultPath;
        }
    }
}
