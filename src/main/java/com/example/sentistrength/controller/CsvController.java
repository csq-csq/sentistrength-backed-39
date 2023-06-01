package com.example.sentistrength.controller;

import com.example.sentistrength.result.Result;
import com.example.sentistrength.result.ResultFactory;
import com.opencsv.CSVReader;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CsvController {
    @GetMapping("/api/getCSV")
    public Result getCSV() {
        System.out.println("access controller");
        List<Map<String, String>> result = new ArrayList<>();
        String directoryPath = "/home/SE3/sentiSpring/proresult"; // 修改为你的目录路径

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            System.out.println("find dictory");
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .forEach(path -> {
                        try (CSVReader reader = new CSVReader(new FileReader(path.toString()))) {
                            String[] lineInArray;
                            String[] header = reader.readNext(); // assuming first read
                            while ((lineInArray = reader.readNext()) != null) {
                                Map<String, String> lineMap = new HashMap<>();
                                for (int i = 0; i < header.length; i++) {
                                    lineMap.put(header[i], lineInArray[i]);
                                }
                                result.add(lineMap);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultFactory().buildSuccessResult(result);
    }
}