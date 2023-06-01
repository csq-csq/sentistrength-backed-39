package com.example.sentistrength;

import com.example.sentistrength.service.PathService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.io.BufferedReader;
import java.io.File;

@SpringBootApplication
public class SentistrengthApplication {
    public static void main(String[] args) {

        SpringApplication.run(SentistrengthApplication.class, args);
      /*  *//*String[] arg={"trinary","annotateCol","6","overwrite","uploader","E:\\SE3\\dsfw.txt","downloader","E:\\SE3\\dsfw.txt"};
        File directory = new File("E:\\SE3\\dsfw.txt");
        System.out.println(directory.exists());*//*
        SentiStrength.main(arg);*/
    }

}
