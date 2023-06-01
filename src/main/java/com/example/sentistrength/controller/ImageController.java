package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.ImageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ImageController {

    private static final String IMAGES_PATH = "/path/to/your/images";
    private ImageService imageService;
    @GetMapping("/getImage")
    public Result getAllImages() throws IOException, InterruptedException {
        System.out.println("access controller!");
        File folder = new File(IMAGES_PATH);
        File[] listOfFiles = folder.listFiles();
        List<String> result = new ArrayList<>();
        imageService.runPy();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                // assuming that your app is running on localhost:8080
                result.add("http://8.130.116.36/home/SE3/sentiSpring/pic" + listOfFiles[i].getName());
            }
        }

        return ResultFactory.buildSuccessResult(result);
    }
}
