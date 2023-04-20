package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class UploadController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile[] files){
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            System.out.println(filename);
            String type = filename.substring(filename.lastIndexOf(".") + 1);
            String date = String.valueOf(System.currentTimeMillis());

            File upLoadParentFile = new File(fileUploadPath);
            if (!upLoadParentFile.exists()) {
                upLoadParentFile.mkdirs();
            }

            File uploadFile = new File(fileUploadPath + filename.substring(0, filename.lastIndexOf(".")) + date + "." + type);
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultFactory.buildSuccessResult("upload success");
    }
}
