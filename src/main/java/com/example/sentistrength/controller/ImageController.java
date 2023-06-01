package com.example.sentistrength.controller;

import com.example.sentistrength.service.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {

    private static final String IMAGES_PATH = "/path/to/your/images";
    private ImageService imageService;
    @GetMapping("/getImage")
    public List<String> getAllImages() throws IOException, InterruptedException {
        File folder = new File(IMAGES_PATH);
        File[] listOfFiles = folder.listFiles();
        List<String> imagesUrls = new ArrayList<>();
        imageService.runPy();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                // assuming that your app is running on localhost:8080
                imagesUrls.add("http://8.130.116.36/home/SE3/sentiSpring/pic" + listOfFiles[i].getName());
            }
        }

        return imagesUrls;
    }
}
