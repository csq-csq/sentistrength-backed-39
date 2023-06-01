package com.example.sentistrength.controller;

import com.example.sentistrength.entity.DateListRequest;
import com.example.sentistrength.entity.DateValue;
import com.example.sentistrength.entity.DatesWrapper;
import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.example.sentistrength.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PythonScriptController {
    @Autowired
    private CrawlerService crawlerService;



    @PostMapping("/spider")
    public Result runPythonScript(@RequestBody DatesWrapper datesWrapper) {
        System.out.println("access controller");
        List<List<String>> datesList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (DateValue dateValue : datesWrapper.getDates()) {
            List<String> dateSubList = new ArrayList<>();
            for (String dateString : dateValue.getValue()) {
                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                LocalDate date = dateTime.toLocalDate();
                dateSubList.add(date.format(outputFormatter));
            }
            datesList.add(dateSubList);
        }
        try {
            System.out.println("access service");
/*            File directory = new File("/home/SE3/sentiSpring/data");
            File directory2 = new File("/home/SE3/sentiSpring/result");
            File directory3 = new File("/home/SE3/sentiSpring/proresult");
            deleteFilesAndSubdirectories(directory);
            deleteFilesAndSubdirectories(directory2);
            deleteFilesAndSubdirectories(directory3);*/
            crawlerService.runPythonScript(datesList);
            return new ResultFactory().buildSuccessResult("爬取成功");
        } catch (Exception e) {
            return new ResultFactory().buildFailResult("爬取失败");
        }
    }

    public static void deleteFilesAndSubdirectories(File directory) {
        if (directory.isDirectory()) {
            // directory is a file directory -> get all files and subdirectories
            File[] entries = directory.listFiles();
            if (entries != null) {
                // delete all files and subdirectories within this directory
                for (File entry : entries) {
                    deleteFileOrDirectory(entry);
                }
            }
        }
    }

    public static void deleteFileOrDirectory(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            // if directory, recursively delete contents
            for (File child : fileOrDirectory.listFiles()) {
                deleteFileOrDirectory(child);
            }
        }
        // at this point, fileOrDirectory is either a file, or an empty directory, and can be deleted
        boolean isDeleted = fileOrDirectory.delete();
        if (!isDeleted) {
            System.out.println("Failed to delete: " + fileOrDirectory.getName());
        }
    }


}
