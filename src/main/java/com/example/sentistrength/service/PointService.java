package com.example.sentistrength.service;

import org.springframework.stereotype.Service;
import uk.ac.wlv.sentistrength.SentiStrength;

import java.io.File;

@Service
public class PointService {
    public void getpoint() {
        String[] order1={"annotateCol","6","overwrite","uploader","/home/SE3/sentiSpring/result","downloader","/home/SE3/sentiSpring/result"};
        String[] order2={"trinary","annotateCol","6","overwrite","uploader","/home/SE3/sentiSpring/result","downloader","/home/SE3/sentiSpring/result"};
        String[] order3={"scale","annotateCol","6","overwrite","uploader","/home/SE3/sentiSpring/result","downloader","/home/SE3/sentiSpring/result"};
        String directoryPath = "/home/SE3/sentiSpring/result";
        SentiStrength.main(order1);
        SentiStrength.main(order2);
        SentiStrength.main(order3);
       /* // 创建 File 对象，代表该路径
        File directory = new File(directoryPath);

        // 判断该路径是否存在且是否是一个目录
        if(directory.exists() && directory.isDirectory()){
            // 使用 listFiles 方法获取文件夹内的所有文件
            File[] files = directory.listFiles();

            // 遍历文件
            for(File file : files){
                // 判断文件后缀是否为.txt
                if(file.getName().endsWith(".txt")){
                    // 打印文件的完整路径
                    System.out.println(file.getAbsolutePath());
                    order1[3]=file.getAbsolutePath();
                    System.out.println(order1[3]);
                    order2[4]=file.getAbsolutePath();
                    System.out.println(order2[4]);
                    order3[4]=file.getAbsolutePath();
                    SentiStrength.main(order1);
                    SentiStrength.main(order2);
                    SentiStrength.main(order3);
                }
            }
        } else {
            System.out.println("The directory does not exist or is not a directory.");
        }*/

    }
}
