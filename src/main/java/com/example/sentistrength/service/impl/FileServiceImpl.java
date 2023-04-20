package com.example.sentistrength.service.impl;

import com.example.sentistrength.entity.vo.FileVO;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Override
    public FileVO upload(MultipartFile file){
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String type = filename.substring(filename.lastIndexOf(".") + 1);
        String date = String.valueOf(System.currentTimeMillis());

        File upLoadParentFile = new File(fileUploadPath);
        if (!upLoadParentFile.exists()) {
            upLoadParentFile.mkdirs();
        }

        File uploadFile = new File(fileUploadPath + filename.substring(0, filename.lastIndexOf("."))+ "." + type);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileVO(fileUploadPath,filename);
    }
}
