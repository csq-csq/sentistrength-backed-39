package com.example.sentistrength.controller;

import ch.qos.logback.core.util.FileUtil;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file){
        String filename=file.getOriginalFilename();
        System.out.println(filename);
        String type = filename.substring(filename.lastIndexOf(".")+1);
        String date = String.valueOf(System.currentTimeMillis());

        File upLoadParentFile = new File(fileUploadPath);
        if(!upLoadParentFile.exists()){
            upLoadParentFile.mkdirs();
        }

        File uploadFile = new File(fileUploadPath+filename.substring(0,filename.lastIndexOf("."))+date+"."+type);
        try {
            file.transferTo(uploadFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ResultFactory.buildSuccessResult(uploadFile.getName());
    }
}
