package com.example.sentistrength.controller;

import com.example.sentistrength.entity.vo.FileVO;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/file")
public class FileController {

//    @Value("${files.upload.path}")
//    private String fileUploadPath;
//
//
//    @PostMapping("/upload2")
//    public Result upload(@RequestParam MultipartFile file){
//        String filename = file.getOriginalFilename();
//        System.out.println(filename);
//        String type = filename.substring(filename.lastIndexOf(".") + 1);
//        String date = String.valueOf(System.currentTimeMillis());
//
//        File upLoadParentFile = new File(fileUploadPath);
//        if (!upLoadParentFile.exists()) {
//            upLoadParentFile.mkdirs();
//        }
//
//        File uploadFile = new File(fileUploadPath + filename.substring(0, filename.lastIndexOf("."))+ "." + type);
//        try {
//            file.transferTo(uploadFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return ResultFactory.buildSuccessResult("upload success");
//    }

    @Autowired
    FileService fileService;

    @PostMapping("/upload2")
    public Result upload(MultipartFile file) throws IOException {
        System.out.println("upload--file is"+file);
        //调用service中的业务方法
        FileVO fileVO = fileService.upload(file);
        return ResultFactory.buildSuccessResult(fileVO);
    }

    @GetMapping("/download")
    public Object downloadFile(HttpServletResponse response, String filePath) throws IOException {
        // 清空输出流
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(filePath.getBytes("utf-8"), "utf-8"));
        fileService.download(response.getOutputStream(),filePath);
        return null;
    }

    @PostMapping("/deleteAllUpload")
    public Result deleteAllUpload(MultipartFile file){
        fileService.deleteAllUpload();
        return ResultFactory.buildSuccessResult("DeleteSuccess");
    }

}
