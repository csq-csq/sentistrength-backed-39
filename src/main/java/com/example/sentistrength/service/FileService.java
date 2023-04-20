package com.example.sentistrength.service;


import com.example.sentistrength.entity.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;

public interface FileService {
    //文件上传
    public FileVO upload(MultipartFile file);
}
