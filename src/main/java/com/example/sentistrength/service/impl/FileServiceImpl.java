package com.example.sentistrength.service.impl;

import com.example.sentistrength.entity.vo.FileVO;
import com.example.sentistrength.service.FileService;
import com.example.sentistrength.service.PathService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    PathService pathService;


    @Override
    public FileVO upload(MultipartFile file){
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String type = filename.substring(filename.lastIndexOf(".") + 1);
        String date = String.valueOf(System.currentTimeMillis());

        File upLoadParentFile = new File(pathService.getUploadPath());
        upLoadParentFile.delete();
        upLoadParentFile.mkdirs();

        File uploadFile = new File(pathService.getUploadPath() + filename.substring(0, filename.lastIndexOf("."))+ "." + type);
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileVO(pathService.getUploadPath(),filename);
    }

    @Override
    public Object download(OutputStream os, String filePath) throws IOException {
        //下载文件的路径
        String downPath = pathService.getResultPath()+filePath;
        //读取目标文件
        File f = new File(downPath);
        //创建输入流
        InputStream is = new FileInputStream(f);
        //做一些业务判断，我这里简单点直接输出，你也可以封装到实体类返回具体信息
        if (is == null) {
            System.out.println("文件不存在");
        }
        //利用IOUtils将输入流的内容 复制到输出流
        //org.apache.tomcat.util.http.fileupload.IOUtils
        //项目搭建是自动集成了这个类 直接使用即可
        IOUtils.copy(is, os);
        os.flush();
        is.close();
        os.close();
        return null;
    }

    @Override
    public void deleteAllUpload(){
        pathService.deleteFiles(pathService.getUploadPath());
    }

}
